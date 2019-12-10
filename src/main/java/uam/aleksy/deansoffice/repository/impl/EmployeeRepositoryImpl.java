package uam.aleksy.deansoffice.repository.impl;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    private static final int NUM_OF_EMPLOYEES = 10;

    private static final int EMPLOYEE_ENERGY = 4;

    @PostConstruct
    private void createEmployees() {
        IntStream.rangeClosed(1, NUM_OF_EMPLOYEES).forEach(i -> {
            Employee e = new Employee(null);
            e.setName(e.hashCode()+"");
            employees.add(e);
        });
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public List<Employee> getEmployeesWithEnergy() {
        return employees
                .stream()
                .filter(employee -> employee.getEnergyLeft() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getBusyEmployees() {
        return employees.stream()
                .filter(employee -> employee.isBusy())
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Employee> findFreeEmployee() {
        return employees.stream().filter(employee -> !employee.isBusy()).findFirst();
    }

    @Override
    public void resetEmployeesEnergy() {
        employees.forEach(employee -> employee.setEnergyLeft(EMPLOYEE_ENERGY));
    }
}
