package uam.aleksy.deansoffice.employee;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomEmployeeFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private static final int NUM_OF_EMPLOYEES = 3;

    private List<Employee> employees;
    private BiMap<Employee, Applicant> employeeApplicantBiMap;

    @PostConstruct
    private void createEmployees() {
        employeeApplicantBiMap = HashBiMap.create();
        employees = RandomEmployeeFactory.getRandomEmployees(NUM_OF_EMPLOYEES);
    }

    public List<Employee> getEmployees(Predicate<Employee> predicate) {
        return employees.stream().filter(predicate).collect(Collectors.toList());
    }

    public Applicant getEmployeesApplicant(Employee employee) {
        return employeeApplicantBiMap.get(employee);
    }

    public void assignEmployeeToApplicant(Employee employee, Applicant applicant) {
        employeeApplicantBiMap.put(employee, applicant);
    }

    public void freeEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
    }

    public void removeEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
    }

    public Employee getApplicantsEmployee(Applicant applicant) {
        return employeeApplicantBiMap.inverse().get(applicant);
    }
}
