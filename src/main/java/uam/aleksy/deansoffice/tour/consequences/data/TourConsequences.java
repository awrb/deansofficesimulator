package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.enums.Activity;

import java.util.Map;

public class TourConsequences {

    private Map<Long, Integer> applicantIdToConsequences;

    private Map<String, Activity> employeeNameToActivity; // co robi każdy z pracowników dziekanatu

    private int expectedWaitingTime; // jaki jest teoretyczny czas oczekiwania

    private Map<Class<? extends Applicant>, Integer> queue; // ile jest osób w kolejce + ile jakich
}
