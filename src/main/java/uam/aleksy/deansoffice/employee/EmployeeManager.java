package uam.aleksy.deansoffice.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;

@Component
public class EmployeeManager {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void fireEmployeeByApplicant(Applicant applicant) {
        employeeRepository.removeEmployee(employeeRepository.getApplicantsEmployee(applicant));
    }
}
