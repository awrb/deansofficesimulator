package uam.aleksy.deansoffice.core;

import lombok.AccessLevel;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Activity;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.common.api.WorkCoordinationService;
import uam.aleksy.deansoffice.service.common.impl.WorkCoordinationServiceImpl;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Log
@Service
public class SimulationRunner implements CommandLineRunner {

    private OfficeQueue queue;

    private ApplicantRepository applicantRepository;

    private SimulationStatePublisher simulationStatePublisher;

    private WorkCoordinationService workCoordinationService;

    private EmployeeRepository employeeRepository;

    @Autowired
    public SimulationRunner(OfficeQueue queue, ApplicantRepository applicantRepository,
                            SimulationStatePublisher simulationStatePublisher,
                            WorkCoordinationServiceImpl workCoordinationService,
                            EmployeeRepository employeeRepository) {
        this.queue = queue;
        this.applicantRepository = applicantRepository;
        this.simulationStatePublisher = simulationStatePublisher;
        this.workCoordinationService = workCoordinationService;
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    private void init() {
        // populate queue with some applicants so the simulation can run
        queue.flush();
        queue.addAll(applicantRepository.getApplicants());
    }

    private void slowSimulationDown() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
    }

    @Override
    public void run(String... args) {

        simulationStatePublisher.notifyStart();

        Predicate<Employee> energyLeftPredicate = employee -> employee.getEnergyLeft() > 0;


        while (!queue.isEmpty() && !workCoordinationService.helpedEveryApplicant()) {
            log.info("Remaining in queue: " + queue.getQueue().size());

            // continue as long as every employee isn't out of energy - that's when the round is over
            List<Employee> employeesWithEnergy = employeeRepository
                    .getEmployees(energyLeftPredicate);

            while (!employeesWithEnergy.isEmpty() && !queue.isEmpty()) {

                employeesWithEnergy.forEach(employee -> {

                    slowSimulationDown();

                    // employee is busy, as in there's an applicant assigned to him - continue working with him
                    if (employee.isBusy()) {

                        Applicant applicant = employeeRepository.getEmployeesApplicant(employee);

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
                    log.info("Start helping");

                    if (employee.canWorkOnTask(task)) {
                        log.info("WORK WOKR");
                        workCoordinationService.workOnNewTask(employee, applicant, task);
                        return;
                    }

                    // employee is too tired to help this applicant
                    employee.finishWork();
                });


                employeesWithEnergy = employeeRepository
                        .getEmployees(energyLeftPredicate);
                log.info(""+employeesWithEnergy.size());
            }



            // the round is over
            simulationStatePublisher.notifyNextTour();
        }

        log.info("Queue is empty");
    }
}
