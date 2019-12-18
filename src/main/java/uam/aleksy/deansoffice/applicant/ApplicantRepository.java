package uam.aleksy.deansoffice.applicant;


import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Repository
public class ApplicantRepository {

    private Map<Tour, Set<Applicant>> applicantsInTour;

    @PostConstruct
    private void init() {
        applicantsInTour = new HashMap<>();
    }

    public void addApplicantsInTour(Tour tour, Set<Applicant> applicants) {
        applicantsInTour.put(tour, applicants);
    }

}
