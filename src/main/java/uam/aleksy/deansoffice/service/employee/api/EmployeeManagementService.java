package uam.aleksy.deansoffice.service.employee.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.data.Task;

import java.util.List;
import java.util.Optional;

public interface EmployeeManagementService {
    List<Employee> getEmployees();

    List<Employee> getEmployeesWithEnergy();

    List<Employee> getBusyEmployees();

    Optional<Employee> findFreeEmployee();

    void resetEmployeesEnergy();

    void finishWork(Employee employee);

    void finishHelping(Employee employee);

    void workOnTask(Employee employee, Task task);

    boolean employeeCanPerformTask(Employee employee, Task task);

    Applicant getEmployeesApplicant(Employee employee);

    void assignApplicantToEmployee(Applicant applicant, Employee employee);

    void continueHelpingApplicant(Employee employee);

    void startHelpingNextApplicant(Employee employee);
}
