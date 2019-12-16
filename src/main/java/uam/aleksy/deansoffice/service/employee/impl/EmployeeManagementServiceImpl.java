package uam.aleksy.deansoffice.service.employee.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.core.SimulationStateListener;
import uam.aleksy.deansoffice.core.SimulationStatePublisher;
import uam.aleksy.deansoffice.data.Employee;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;

import javax.annotation.PostConstruct;

@Service
public class EmployeeManagementServiceImpl implements EmployeeManagementService, SimulationStateListener {

    private SimulationStatePublisher simulationStatePublisher;

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeManagementServiceImpl(SimulationStatePublisher simulationStatePublisher, EmployeeRepository employeeRepository) {
        this.simulationStatePublisher = simulationStatePublisher;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    private void init() {
        simulationStatePublisher.registerListener(this);
    }

    public void fireEmployee(Employee employee) {
        employeeRepository.removeEmployee(employee);
    }

    @Override
    public void nextTour() {
        employeeRepository.getEmployees(employee -> true).forEach(employee -> {
            employee.resetEnergy();
            employee.setBusy(false);
        });
    }

    @Override
    public void onStart() {

    }
}
