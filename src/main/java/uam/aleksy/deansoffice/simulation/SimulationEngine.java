package uam.aleksy.deansoffice.simulation;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.coordination.WorkCoordinationService;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.queue.QueueDataGenerator;
import uam.aleksy.deansoffice.tour.TourManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@Log
public class SimulationEngine {

    private OfficeQueue queue;
    private EmployeeRepository employeeRepository;
    private WorkCoordinationService workCoordinationService;
    private TourManager tourManager;
    private QueueDataGenerator queueDataGenerator;
    private int simulationDelay; // miliseconds between every tour

    public SimulationEngine(OfficeQueue queue,
                            EmployeeRepository employeeRepository,
                            WorkCoordinationService workCoordinationService,
                            TourManager tourManager,
                            QueueDataGenerator queueDataGenerator,
                            @Value("${simulation.delay}") int simulationDelay) {
        this.queue = queue;
        this.employeeRepository = employeeRepository;
        this.workCoordinationService = workCoordinationService;
        this.tourManager = tourManager;
        this.queueDataGenerator = queueDataGenerator;
        this.simulationDelay = simulationDelay;
    }


    private void slowSimulationDown() {
        try {
            // todo inject from properties
            Thread.sleep(simulationDelay);
        } catch (InterruptedException e) {
        }
    }

    void runSimulation() {
        Predicate<Employee> energyLeftPredicate = employee -> employee.getEnergyLeft() > 0;

        while (!employeeRepository.getEmployees(employee -> true).isEmpty()) {

            if (queue.isEmpty()) {
                queue.addAll(queueDataGenerator.generateApplicants());

            }

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
                            tourManager.addApplicant(applicant);

                            Task task = nextTask.get();

                            if (employee.canWorkOnTask(task)) {
                                workCoordinationService.workOnNewTask(employee, applicant, task);
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

                    if (!employee.isWorking()) {
                        log.info(employee.getName() + " is not working, current activity: "
                                + employee.getActivityCycle().get(employee.getCurrentActivityIndex()));
                        employee.finishWork();
                        return;
                    }


                    Optional<Task> task = applicant.getNextTask();

                    queue.remove();

                    if (!task.isPresent()) {
                        // when someone comes with no tasks
                        return;
                    }

                    workCoordinationService.startHelping(employee, applicant);

                    if (employee.canWorkOnTask(task.get())) {
                        workCoordinationService.workOnNewTask(employee, applicant, task.get());
                        return;
                    }

                    // employee is too tired to help this applicant
                    employee.finishWork();
                });


                employeesWithEnergy = employeeRepository
                        .getEmployees(energyLeftPredicate);
            }

            // the round is over
            tourManager.finishRound();
        }

        if (queue.getQueue().isEmpty()) {
            log.info("Queue is empty");
        } else {
            log.info("All employees have been fired");
        }
    }
}
