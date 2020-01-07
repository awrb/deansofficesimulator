package uam.aleksy.deansoffice.coordination;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@Log
@Service
public class WorkCoordinationService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public WorkCoordinationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void workOnNewTask(Employee employee, Applicant applicant, Task task) {
        log.info(employee.getName() + " now helping " + applicant.getClass().getSimpleName()
                + " " + applicant.getName()
                + ", id " + applicant.getId()
                + " with task of difficulty "
                + task.getDifficulty());
        employee.workOnTask(task);
        applicant.removeTask();
    }

    public void finishHelping(Employee employee, Applicant applicant) {
        employeeRepository.unassignEmployee(employee);
        employee.setBusy(false);
        log.info(employee.getName() + " finished helping " + applicant.getName());
    }

    public void startHelping(Employee employee, Applicant applicant) {
        employeeRepository.assignEmployeeToApplicant(employee, applicant);
    }
}
