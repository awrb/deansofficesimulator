package uam.aleksy.deansoffice.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.NextTourListener;
import uam.aleksy.deansoffice.tour.NextTourPublisher;

import javax.annotation.PostConstruct;
import java.util.function.Predicate;

@Service
public class EmployeeManagementService implements NextTourListener {


    private EmployeeRepository employeeRepository;

    private NextTourPublisher nextTourPublisher;

    @Autowired
    public EmployeeManagementService(EmployeeRepository employeeRepository, NextTourPublisher nextTourPublisher) {
        this.employeeRepository = employeeRepository;
        this.nextTourPublisher = nextTourPublisher;
    }

    @PostConstruct
    private void init() {
        nextTourPublisher.registerListener(this);
    }

    public void fireEmployee(Employee employee) {
        employeeRepository.removeEmployee(employee);
    }

    @Override
    public void nextTour() {
        employeeRepository.getEmployees(employee -> true).forEach(employee -> {
            employee.toggleNextActivity();
            employee.resetEnergy();
        });
    }
}
