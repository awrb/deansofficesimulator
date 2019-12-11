package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.queue.QueueStateManager;
import uam.aleksy.deansoffice.service.applicant.api.ApplicantManagementService;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;

import javax.annotation.PostConstruct;
import java.util.List;


@Log
@Service
public class SimulationRunner implements CommandLineRunner {

    @Autowired
    private OfficeQueue queue;

    @Autowired
    private EmployeeManagementService employeeManagementService;

    @Autowired
    private ApplicantManagementService applicantManagementService;

    @Autowired
    private QueueStateManager queueStateManager;


    @PostConstruct
    private void init() {
        // populate queue with some applicants so the simulation can run
        queue.addAll(applicantManagementService.getApplicants());
    }

    @Override
    public void run(String... args) {

        while (!queue.isEmpty()) {
            log.info("Remaining in queue: " + queue.getQueue().size());

            // continue as long as every employee isn't out of energy - that's when the round is over
            List<Employee> employeesWithEnergy = employeeManagementService.getEmployeesWithEnergy();

            while (!employeesWithEnergy.isEmpty() && !queue.isEmpty()) {

                employeesWithEnergy.forEach(employee -> {
                    // employee is busy, as in there's an applicant assigned to him - continue working with him
                    if (employee.isBusy()) {
                        employeeManagementService.continueHelpingApplicant(employee);
                    } else {
                        // employee is not busy, get a new person from the queue for him
                        employeeManagementService.startHelpingNextApplicant(employee);
                    }
                });

                employeesWithEnergy = employeeManagementService.getEmployeesWithEnergy();
            }
            // the round is over
            queueStateManager.notifyNextRound();
        }

        log.info("Queue is empty");
    }
}
