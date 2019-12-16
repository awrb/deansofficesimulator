package uam.aleksy.deansoffice.data;

import java.util.Map;

public class TourConsequences {

    private Map<Long, Integer> applicantIdToConsequences;

    private Map<String, Activity> employeeNameToActivity; // co robi każdy z pracowników dziekanatu

    private int expectedWaitingTime; // jaki jest teoretyczny czas oczekiwania

    private Map<Class<? extends Applicant>, Integer> queue; // ile jest osób w kolejce + ile jakich
}
