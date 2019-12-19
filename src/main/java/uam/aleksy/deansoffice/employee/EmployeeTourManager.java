package uam.aleksy.deansoffice.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.tour.NextTourListener;
import uam.aleksy.deansoffice.tour.NextTourPublisher;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;

@Component
public class EmployeeTourManager implements NextTourListener {

    private EmployeeRepository employeeRepository;

    private NextTourPublisher nextTourPublisher;

    @Autowired
    public EmployeeTourManager(EmployeeRepository employeeRepository, NextTourPublisher nextTourPublisher) {
        this.employeeRepository = employeeRepository;
        this.nextTourPublisher = nextTourPublisher;
    }

    @PostConstruct
    private void init() {
        nextTourPublisher.registerListener(this);
    }

    @Override
    public void nextTour(Tour tour) {
        employeeRepository.getEmployees(employee -> true).forEach(employee -> {
            employee.toggleNextActivity();
            employee.resetEnergy();
        });
    }
}
