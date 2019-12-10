package uam.aleksy.deansoffice.service.employee.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> getEmployees() {
        return repository.getEmployees();
    }

    @Override
    public List<Employee> getEmployeesWithEnergy() {
        return repository.getEmployeesWithEnergy();
    }

    @Override
    public List<Employee> getBusyEmployees() {
        return repository.getBusyEmployees();
    }

    @Override
    public Optional<Employee> findFreeEmployee() {
        return repository.findFreeEmployee();
    }

    @Override
    public void resetEmployeesEnergy() {
        repository.resetEmployeesEnergy();
    }
}
