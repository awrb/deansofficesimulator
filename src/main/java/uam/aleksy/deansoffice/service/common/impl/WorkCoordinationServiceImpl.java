package uam.aleksy.deansoffice.service.common.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.common.api.WorkCoordinationService;

import java.util.List;

@Log
@Service
public class WorkCoordinationServiceImpl implements WorkCoordinationService {

    private ApplicantRepository applicantRepository;

    private EmployeeRepository employeeRepository;

    private OfficeQueue queue;

    @Autowired
    public WorkCoordinationServiceImpl(ApplicantRepository applicantRepository, EmployeeRepository employeeRepository,
                                       OfficeQueue queue) {
        this.applicantRepository = applicantRepository;
        this.employeeRepository = employeeRepository;
        this.queue = queue;
    }

    @Override
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

    @Override
    public void continueHelping(Employee employee, Applicant applicant, Task task) {
        workOnNewTask(employee, applicant, task);
        log.info(employee.getName() + " now helping " + applicant.getName() + " with task of difficulty " + task.getDifficulty());

    }

    @Override
    public void workOnNewTask(Employee employee, Applicant applicant, Task task) {
        employee.workOnTask(task);
        applicant.removeTask();
    }

    @Override
    public void finishHelping(Employee employee, Applicant applicant) {
        employeeRepository.freeEmployee(employee);
        employee.setBusy(false);
        log.info(employee.getName() + " finished helping " + applicant.getName());
    }

    @Override
    public void startHelping(Employee employee, Applicant applicant) {
        queue.remove();
        employeeRepository.assignEmployeeToApplicant(employee, applicant);
    }
}
