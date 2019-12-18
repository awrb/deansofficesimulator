package uam.aleksy.deansoffice.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.NextTourListener;
import uam.aleksy.deansoffice.tour.NextTourPublisher;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;

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
    public void nextTour(Tour tour) {
        employeeRepository.getEmployees(employee -> true).forEach(employee -> {
            employee.toggleNextActivity();
            employee.resetEnergy();
        });
    }
}
