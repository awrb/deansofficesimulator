package uam.aleksy.deansoffice.repository.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomEmployeeFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final int NUM_OF_EMPLOYEES = 5;

    private List<Employee> employees;
    private BiMap<Employee, Applicant> employeeApplicantBiMap;

    @PostConstruct
    private void createEmployees() {
        employeeApplicantBiMap = HashBiMap.create();
        employees = RandomEmployeeFactory.getRandomEmployees(NUM_OF_EMPLOYEES);
    }

    @Override
    public List<Employee> getEmployees(Predicate<Employee> predicate) {
        if (predicate != null) {
            return employees.stream().filter(predicate).collect(Collectors.toList());
        }

        return employees;
    }

    @Override
    public Applicant getEmployeesApplicant(Employee employee) {
        return employeeApplicantBiMap.get(employee);
    }

    @Override
    public void assignEmployeeToApplicant(Employee employee, Applicant applicant) {
        employeeApplicantBiMap.put(employee, applicant);
    }

    @Override
    public void freeEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
    }

    @Override
    public void removeEmployee(Employee employee) {
        employeeApplicantBiMap.remove(employee);
    }

    @Override
    public Employee getApplicantsEmployee(Applicant applicant) {
        return employeeApplicantBiMap.inverse().get(applicant);
    }
}
