package uam.aleksy.deansoffice.repository.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> getEmployees();

    List<Employee> getEmployeesWithEnergy();

    List<Employee> getBusyEmployees();

    Optional<Employee> findFreeEmployee();

    Applicant getEmployeesApplicant(Employee employee);

    void assignEmployeeToApplicant(Employee employee, Applicant applicant);

    void resetEmployeesEnergy();
}
