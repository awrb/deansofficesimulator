package uam.aleksy.deansoffice.service.employee.api;

import uam.aleksy.deansoffice.data.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeManagementService {
    List<Employee> getEmployees();

    List<Employee> getEmployeesWithEnergy();

    List<Employee> getBusyEmployees();

    Optional<Employee> findFreeEmployee();

    void resetEmployeesEnergy();
}
