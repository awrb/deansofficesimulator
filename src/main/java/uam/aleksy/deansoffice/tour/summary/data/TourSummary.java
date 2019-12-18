package uam.aleksy.deansoffice.tour.summary.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.enums.Activity;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;

import java.util.List;
import java.util.Map;

public class TourSummary {

    private List<Consequence> consequences;
    private Map<String, Activity> employeeNameToActivity; // co robi każdy z pracowników dziekanatu
    private int expectedWaitingTime; // jaki jest teoretyczny czas oczekiwania
    private Map<Class<? extends Applicant>, Integer> applicantTypeCounters; // ile jest osób w kolejce
    private Long tourId;
    private int queueSize; // ile jakich

    public TourSummary() {
    }

    public Long getTourId() {
        return tourId;
    }

    public TourSummary(List<Consequence> consequences, Map<String, Activity> employeeNameToActivity,
                       int expectedWaitingTime, Map<Class<? extends Applicant>, Integer> applicantTypeCounters,
                       int queueSize,
                       Long tourId) {
        this.consequences = consequences;
        this.employeeNameToActivity = employeeNameToActivity;
        this.expectedWaitingTime = expectedWaitingTime;
        this.applicantTypeCounters = applicantTypeCounters;
        this.queueSize = queueSize;
        this.tourId = tourId;
    }

    public List<Consequence> getConsequences() {
        return consequences;
    }

    public Map<String, Activity> getEmployeeNameToActivity() {
        return employeeNameToActivity;
    }

    public int getExpectedWaitingTime() {
        return expectedWaitingTime;
    }

    public Map<Class<? extends Applicant>, Integer> getApplicantTypeCounters() {
        return applicantTypeCounters;
    }

    public int getQueueSize() {
        return queueSize;
    }
}
