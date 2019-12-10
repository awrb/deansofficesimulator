package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;
import uam.aleksy.deansoffice.service.applicant.api.ApplicantManagementService;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log
@Service
public class SimulationRunner implements CommandLineRunner {

    private Map<Employee, Applicant> employeeApplicantMap;

    @Autowired
    private OfficeQueue queue;

    @Autowired
    private EmployeeManagementService employeeManagementService;

    @Autowired
    private ApplicantManagementService applicantManagementService;


    @PostConstruct
    private void init() {
        employeeApplicantMap = new HashMap<>();

        // populate queue with some applicants
        queue.addAll(applicantManagementService.getApplicants());
    }

    @Override
    public void run(String ...args) {


        while (!queue.isEmpty()) {
            log.info("Remaining in queue: " + queue.getQueue().size());

            // continue as long as every employee isn't out of energy
            List<Employee> employeesThatCanWork = employeeManagementService.getEmployeesWithEnergy();


            while (!employeesThatCanWork.isEmpty() && !queue.isEmpty()) {

                employeesThatCanWork.forEach(employee -> {

                    // employee is busy, as in there's an applicant assigned to him - continue working with him
                    if (employee.isBusy()) {
                        Applicant applicant = employeeApplicantMap.get(employee);

                        List<Task> taskCopy = applicant.getTasks();

                        if (taskCopy.size() > 0) {//
                            Task task = applicant.getTasks().get(applicant.getTasks().size() - 1);
                            log.info(employee.getName() + " still helping " + applicant.getName() + ", new task has difficulty " + task.getDifficulty());

                            if (employee.getEnergyLeft() > task.getDifficulty()) {
                                // work
                                employee.setEnergyLeft(employee.getEnergyLeft() - task.getDifficulty());


                                taskCopy.remove(taskCopy.size() - 1);
                                applicant.setTasks(taskCopy);
                            } else {
                                // employee is too tired, set his energy to 0 anyway
                                employee.setEnergyLeft(0);
                                employee.setBusy(false);
                            }
                        } else {//
                            employee.setBusy(false);
                            employeeApplicantMap.remove(employee);

                            //
                        }

                    } else {
                        // get a new applicant if possible, assign to map

                        Applicant applicant = queue.peek();
                        if (applicant != null) { // to remove
                            Task task = applicant.getTasks().get(applicant.getTasks().size() - 1);

                            log.info(employee.getName() + " now helping " + applicant.getName() + " with task of difficulty " + task.getDifficulty());


                            if (employee.getEnergyLeft() > task.getDifficulty()) {
                                // work
                                queue.remove();
                                employee.setEnergyLeft(employee.getEnergyLeft() - task.getDifficulty());
                                employee.setBusy(true);
                                employeeApplicantMap.put(employee, applicant); // assign employee to applicant

                                List<Task> taskCopy = applicant.getTasks();

                                if (taskCopy.size() > 0) {
                                    taskCopy.remove(taskCopy.size() - 1);
                                    applicant.setTasks(taskCopy);
                                } else {
                                    employee.setBusy(false);
                                    employeeApplicantMap.remove(employee);
                                }

                            } else {
                                employee.setEnergyLeft(0);
                            }

                        } // to remove

                    }
                });


                employeesThatCanWork = employeeManagementService.getEmployeesWithEnergy();
            }

            // the tour is over, reset energies
            employeeManagementService.resetEmployeesEnergy();
        }

        log.info("queue is empty yay");
    }
}
