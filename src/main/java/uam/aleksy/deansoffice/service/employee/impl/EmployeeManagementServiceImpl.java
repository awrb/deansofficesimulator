package uam.aleksy.deansoffice.service.employee.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Activity;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.core.SimulationStateListener;
import uam.aleksy.deansoffice.core.SimulationStateManager;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.applicant.api.ApplicantManagementService;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService, SimulationStateListener {

    private static int FIRST_ACTIVITY_INDEX = 0;

    @Autowired
    private OfficeQueue queue;

    @Autowired
    private ApplicantManagementService applicantManagementService;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private SimulationStateManager simulationStateManager;

    @PostConstruct
    private void init() {
        simulationStateManager.registerListener(this);
    }

    @Override
    public void startHelpingNextApplicant(Employee employee) {
        Applicant applicant = queue.peek();

        if (applicant == null) {
            // queue is already empty
            return;
        }

        if (!isEmployeeWorking(employee)) {
            log.info(employee.getName() + " is not working, current activity: "
                    + employee.getActivityCycle().get(employee.getCurrentActivityIndex()));
            finishWork(employee);
            return;
        }


        Task task = applicantManagementService.getNextTask(applicant);


        if (employeeCanPerformTask(employee, task)) {
            log.info(employee.getName() + " now helping " + applicant.getName() + " with task of difficulty " + task.getDifficulty());
            assignApplicantToEmployee(applicant, employee);
        } else {
            // employee is to tired to help this applicant
            finishWork(employee);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        return repository.getEmployees();
    }

    @Override
    public List<Employee> getEmployeesWithEnergy() {
        return repository.getEmployeesWithEnergy();
    }

    @Override
    public List<Employee> getBusyEmployees() {
        return repository.getBusyEmployees();
    }

    @Override
    public Optional<Employee> findFreeEmployee() {
        return repository.findFreeEmployee();
    }

    @Override
    public void finishWork(Employee e) {
        e.setBusy(false);
        e.setEnergyLeft(0);
    }

    @Override
    public Applicant getEmployeesApplicant(Employee employee) {
        return repository.getEmployeesApplicant(employee);
    }


    @Override
    public void resetEmployeesEnergy() {
        repository.resetEmployeesEnergy();
    }

    @Override
    public boolean employeeCanPerformTask(Employee employee, Task task) {
        return employee.getEnergyLeft() > task.getDifficulty(); // && currentActivity = TASK

    }

    @Override
    public boolean isEmployeeWorking(Employee employee) {
        return employee
                .getActivityCycle()
                .get(employee.getCurrentActivityIndex())
                .equals(Activity.WORK_ON_TASK);
    }

    @Override
    public void workOnTask(Employee employee, Task task) {
        employee.setBusy(true);
        employee.setEnergyLeft(employee.getEnergyLeft() - task.getDifficulty());
    }

    @Override
    public void continueHelpingApplicant(Employee employee) {
        Applicant applicant = getEmployeesApplicant(employee);

        Task task = applicantManagementService.getNextTask(applicant);

        log.info(employee.getName() + " still helping " + applicant.getName() + ", new task has difficulty " + task.getDifficulty());

        if (employeeCanPerformTask(employee, task)) {
            workOnTask(employee, task);
        } else {
            // check reason (TIRED OR BREAK)
            // employee is too tired, finish work for the round
            finishWork(employee);
        }
    }

    @Override
    public void nextRound() {
        resetEmployeesEnergy();
        toggleNextActivity();
    }

    @Override
    public void onStart() {
        resetEmployeesEnergy();
        // set all indices to first activity in the cycle
        repository.getEmployees().forEach(employee -> employee.setCurrentActivityIndex(FIRST_ACTIVITY_INDEX));
    }

    private void assignApplicantToEmployee(Applicant applicant, Employee employee) {
        repository.assignEmployeeToApplicant(employee, applicant);
        workOnTask(employee, applicantManagementService.getNextTask(applicant));
        queue.remove();
    }

    private void toggleNextActivity() {
        repository.getEmployees().forEach(employee -> {
            int currentActivityIndex = employee.getCurrentActivityIndex();

            if (currentActivityIndex == employee.getActivityCycle().size() - 1) {
                employee.setCurrentActivityIndex(FIRST_ACTIVITY_INDEX);
                return;
            }

            employee.setCurrentActivityIndex(currentActivityIndex + 1);
        });
    }


}
