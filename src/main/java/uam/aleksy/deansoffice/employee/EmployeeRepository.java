package uam.aleksy.deansoffice.employee;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Log
public class EmployeeRepository {


    private List<Employee> employees;
    private BiMap<Employee, Applicant> employeeApplicantBiMap;

    @PostConstruct
    private void init() {
        employeeApplicantBiMap = HashBiMap.create();
        employees = new ArrayList<>();
    }

    public List<Employee> getEmployees(Predicate<Employee> predicate) {
        return employees.stream().filter(predicate).collect(Collectors.toList());
    }

    public void removeEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
        employees.remove(employee);
    }

    public Applicant getEmployeesApplicant(Employee employee) {
        return employeeApplicantBiMap.get(employee);
    }

    public void assignEmployeeToApplicant(Employee employee, Applicant applicant) {
        employeeApplicantBiMap.put(employee, applicant);
    }

    public void unassignEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
    }

    // do zwalniania
    public Employee getApplicantsEmployee(Applicant applicant) {
        log.info("Removing employee helping " + applicant.getName());
        Employee employee = employeeApplicantBiMap.inverse().get(applicant);
        return employee;
    }

    public void addEmployees(Collection<Employee> employeesToAdd) {
        employees.addAll(employeesToAdd);
    }
}
