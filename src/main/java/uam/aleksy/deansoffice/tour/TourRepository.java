package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Log
@Repository
public class TourRepository {

    private static final Long FIRST_ID = 0L;

    private AtomicLong tourIdGenerator;

    /**
     * Stores a mapping between queue tours and a list of applicants helped in that tour
     */
    private Map<Tour, Set<Applicant>> tourApplicantsMap;


    @PostConstruct
    private void init() {
        tourIdGenerator = new AtomicLong(FIRST_ID);
        tourApplicantsMap = new HashMap<>();
    }

    public Tour addNewTour(Set<Applicant> applicantList) {
        Tour tour = new Tour(tourIdGenerator.incrementAndGet());

        log.info("Tour number " + tour.getId() + " starting");

        tourApplicantsMap.put(tour, applicantList);
        return tour;
    }

    public Map<Tour, Set<Applicant>> getTourApplicantsMap() {
        return tourApplicantsMap;
    }

    public Set<Applicant> getLastTourApplicants() {
        return tourApplicantsMap.get(new Tour(tourIdGenerator.get()));
    }
}
