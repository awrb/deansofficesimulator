package uam.aleksy.deansoffice.coordination;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;

import java.util.List;

@Log
@Service
public class WorkCoordinationService {

    private ApplicantRepository applicantRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public WorkCoordinationService(ApplicantRepository applicantRepository, EmployeeRepository employeeRepository,
                                   OfficeQueue queue) {
        this.applicantRepository = applicantRepository;
        this.employeeRepository = employeeRepository;
    }

    // na szybko do poprawy
    public boolean helpedEveryApplicant() {
        List<Applicant> applicants = applicantRepository.getApplicants();
        boolean ok = true;

        for (Applicant applicant : applicants) {
            if (!applicant.getTasks().isEmpty()) {
                ok = false;
            }
        }
        return ok;
    }

    public void continueHelping(Employee employee, Applicant applicant, Task task) {
        workOnNewTask(employee, applicant, task);
        log.info(employee.getName() + " now helping " + applicant.getName() + " with task of difficulty "
                + task.getDifficulty());

    }

    public void workOnNewTask(Employee employee, Applicant applicant, Task task) {
        employee.workOnTask(task);
        applicant.removeTask();
    }

    public void finishHelping(Employee employee, Applicant applicant) {
        employeeRepository.freeEmployee(employee);
        employee.setBusy(false);
        log.info(employee.getName() + " finished helping " + applicant.getName());
    }

    public void startHelping(Employee employee, Applicant applicant) {
        employeeRepository.assignEmployeeToApplicant(employee, applicant);
    }
}
