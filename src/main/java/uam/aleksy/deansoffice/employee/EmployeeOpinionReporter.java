package uam.aleksy.deansoffice.employee;

import lombok.extern.java.Log;
import uam.aleksy.deansoffice.employee.data.Employee;

import java.util.List;

@Log
public class EmployeeOpinionReporter {

    private EmployeeRepository employeeRepository;


    public static void reportBestAndWorstEmployees(List<Employee> employees) {
        employees.sort((lhs, rhs) -> rhs.getReputationScore() - lhs.getReputationScore());
        Employee bestEmployee = employees.get(0);
        Employee worstEmployee = employees.get(employees.size() - 1);
        log.info("Best employee: " + bestEmployee.getName() + ", reputation: " + bestEmployee.getReputationScore());
        log.info("Worst employee: " + worstEmployee.getName() + ", reputation: " + worstEmployee.getReputationScore());
    }
}
