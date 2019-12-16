package uam.aleksy.deansoffice.repository.impl;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Tour;
import uam.aleksy.deansoffice.repository.api.TourRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TourRepositoryImpl implements TourRepository {

    /**
     * Stores a mapping between queue tours and a list of applicants helped in that tour
     */
    private Map<Tour, List<Applicant>> tourApplicantsMap;

    @PostConstruct
    private void init() {
        tourApplicantsMap = new HashMap<>();
    }

    public void addNewTour(Tour tour, List<Applicant> applicantList) {
        tourApplicantsMap.put(tour, applicantList);
    }

    public Map<Tour, List<Applicant>> getTourApplicantsMap() {
        return tourApplicantsMap;
    }

    public Tour getLastTour() {
        return null;
    }
}
