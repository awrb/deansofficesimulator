package uam.aleksy.deansoffice.repository.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;

import java.util.List;
import java.util.function.Predicate;

public interface EmployeeRepository {

    List<Employee> getEmployees(Predicate<Employee> predicate);

    Applicant getEmployeesApplicant(Employee employee);

    Employee getApplicantsEmployee(Applicant applicant);

    void assignEmployeeToApplicant(Employee employee, Applicant applicant);

    void freeEmployee(Employee employee);

    void removeEmployee(Employee employee);

}
