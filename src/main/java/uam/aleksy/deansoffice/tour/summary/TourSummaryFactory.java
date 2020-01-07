package uam.aleksy.deansoffice.tour.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.employee.enums.Activity;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

import java.util.HashMap;
import java.util.Map;

@Component
public class TourSummaryFactory {

    private OfficeQueue officeQueue;

    private EmployeeRepository employeeRepository;

    @Autowired
    public TourSummaryFactory(OfficeQueue officeQueue,
                              EmployeeRepository employeeRepository) {
        this.officeQueue = officeQueue;
        this.employeeRepository = employeeRepository;
    }

    TourSummary createTourSummary(Tour tour) {
        return new TourSummary(
                tour.getConsequences(),
                createEmployeeNameToActivityMap(),
                calculateExpectedWaitingTime(),
                createApplicantTypeCounters(),
                officeQueue.getQueue().size(),
                tour.getId()
        );
    }

    private Map<Class<? extends Applicant>, Integer> createApplicantTypeCounters() {
        Map<Class<? extends Applicant>, Integer> applicantTypeCounters = new HashMap<>();

        officeQueue.getQueue()
                .forEach(applicant -> applicantTypeCounters.merge(applicant.getClass(), 1, Integer::sum));
        return applicantTypeCounters;
    }

    /**
     * Calculates expected optimistic waiting time in tours
     *
     * @return amount of tours for the simulation to end after the last tour
     */
    private int calculateExpectedWaitingTime() {
        int totalEnergyRequirement = officeQueue.getQueue().stream()
                .flatMap(applicant -> applicant.getTasks().stream()).mapToInt(Task::getDifficulty).sum();
        int totalEmployeeEnergy = employeeRepository
                .getEmployees(employee -> true).stream().mapToInt(Employee::getInitialEnergy).sum();
        return totalEnergyRequirement / totalEmployeeEnergy;

    }

    private Map<String, Activity> createEmployeeNameToActivityMap() {
        Map<String, Activity> employeeNameToActivityMap = new HashMap<>();

        employeeRepository.getEmployees(employee -> true)
                .forEach(employee -> employeeNameToActivityMap.put(employee.getName(), employee.getCurrentActivity()));
        return employeeNameToActivityMap;
    }
}
