package uam.aleksy.deansoffice.simulation;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.coordination.WorkCoordinationService;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.tour.NextTourPublisher;
import uam.aleksy.deansoffice.tour.TourRepository;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;


@Log
@Service
public class SimulationRunner implements CommandLineRunner {

    private OfficeQueue queue;

    private ApplicantRepository applicantRepository;

    private SimulationStartPublisher simulationStartPublisher;

    private NextTourPublisher nextTourPublisher;

    private WorkCoordinationService workCoordinationService;

    private EmployeeRepository employeeRepository;

    private TourRepository tourRepository;

    @Autowired
    public SimulationRunner(OfficeQueue queue, ApplicantRepository applicantRepository,
                            SimulationStartPublisher simulationStartPublisher,
                            WorkCoordinationService workCoordinationService,
                            EmployeeRepository employeeRepository,
                            NextTourPublisher nextTourPublisher,
                            TourRepository tourRepository) {
        this.queue = queue;
        this.applicantRepository = applicantRepository;
        this.simulationStartPublisher = simulationStartPublisher;
        this.workCoordinationService = workCoordinationService;
        this.employeeRepository = employeeRepository;
        this.nextTourPublisher = nextTourPublisher;
        this.tourRepository = tourRepository;
    }

    @PostConstruct
    private void init() {
        // populate queue with some applicants so the simulation can run
        queue.flush();

        List<Applicant> applicants = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            applicants.add(RandomApplicantFactory.getRandomApplicant());
        });

        queue.addAll(applicants);
    }

    private void slowSimulationDown() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run(String... args) {

        simulationStartPublisher.notifyStart();

        Predicate<Employee> energyLeftPredicate = employee -> employee.getEnergyLeft() > 0;

        while (!queue.isEmpty()) {
            log.info("Remaining in queue: " + queue.getQueue().size());
            Set<Applicant> applicantsInTour = new HashSet<>();

            // continue as long as every employee isn't out of energy - that's when the round is over
            List<Employee> employeesWithEnergy = employeeRepository
                    .getEmployees(energyLeftPredicate);

            while (!employeesWithEnergy.isEmpty() && !queue.isEmpty()) {

                employeesWithEnergy.forEach(employee -> {

                    slowSimulationDown();

                    // employee is busy, as in there's an applicant assigned to him - continue working with him
                    if (employee.isBusy()) {


                        Applicant applicant = employeeRepository.getEmployeesApplicant(employee);

                        applicantsInTour.add(applicant);

                        Optional<Task> nextTask = applicant.getNextTask();

                        if (nextTask.isPresent()) {
                            Task task = nextTask.get();

                            if (employee.canWorkOnTask(task)) {
                                workCoordinationService.continueHelping(employee, applicant, task);
                                return;
                            }

                            employee.finishWork();
                            return;
                        }

                        workCoordinationService.finishHelping(employee, applicant);
                        return;
                    }

                    // employee is not busy, get a new person from the queue for him
                    Applicant applicant = queue.peek();

                    if (applicant == null) {
                        return;
                    }

                    if (!employee.isWorking()) {
                        log.info(employee.getName() + " is not working, current activity: "
                                + employee.getActivityCycle().get(employee.getCurrentActivityIndex()));
                        employee.finishWork();
                        return;
                    }

                    Task task = applicant.getNextTask().orElseThrow(RuntimeException::new); // when someone comes with no tasks

                    workCoordinationService.startHelping(employee, applicant);

                    queue.remove();

                    applicantsInTour.add(applicant);

                    if (employee.canWorkOnTask(task)) {
                        workCoordinationService.workOnNewTask(employee, applicant, task);
                        return;
                    }

                    // employee is too tired to help this applicant
                    employee.finishWork();
                });


                employeesWithEnergy = employeeRepository
                        .getEmployees(energyLeftPredicate);
            }


            // the round is over

            Tour tour = tourRepository.addNewTour(applicantsInTour);

            nextTourPublisher.notifyNextTour(tour);
        }

        log.info("Queue is empty");
    }
}
