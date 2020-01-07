package uam.aleksy.deansoffice.employee;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.employee.enums.EmployeeReputation;
import uam.aleksy.deansoffice.tour.NextTourListener;
import uam.aleksy.deansoffice.tour.NextTourPublisher;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.List;

@Log
@Component
public class EmployeeOpinionTracker implements NextTourListener {

    private EmployeeRepository employeeRepository;

    private NextTourPublisher nextTourPublisher;

    private EmployeeOpinionReporter employeeOpinionReporter;

    public EmployeeOpinionTracker(EmployeeRepository employeeRepository, NextTourPublisher nextTourPublisher) {
        this.employeeRepository = employeeRepository;
        this.nextTourPublisher = nextTourPublisher;
    }

    @PostConstruct
    private void register() {
        nextTourPublisher.registerListener(this);
    }

    @Override
    public void nextTour(Tour tour) {
        List<Employee> employees = employeeRepository.getEmployees(employee -> true);
        employees.forEach(employee -> {
            if (!employee.isWorking()) {
                modifyEmployeeReputation(employee);
            }
        });
        EmployeeOpinionReporter.reportBestAndWorstEmployees(employees);
    }

    private void modifyEmployeeReputation(Employee employee) {
        employee.decrementReputationScore();
        employee.setReputation(determineReputationByScore(employee.getReputationScore()));
    }

    private EmployeeReputation determineReputationByScore(int score) {
        if (score >= 50) {
            return EmployeeReputation.DOBRY_CZLOWIEK;
        }
        if (score >= 30) {
            return EmployeeReputation.SREDNI_CZLOWIEK;
        } else if (score >= 10) {
            return EmployeeReputation.ZLY_CZLOWIEK;
        } else {
            return EmployeeReputation.BARDZO_ZLY_CZLOWIEK;
        }
    }
}

