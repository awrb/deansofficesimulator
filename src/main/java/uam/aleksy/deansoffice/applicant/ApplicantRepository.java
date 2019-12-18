package uam.aleksy.deansoffice.applicant;


import com.github.javafaker.App;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.IntStream;


@Repository
public class ApplicantRepository {

    private static final int NUM_OF_APPLICANTS = 100;

    private List<Applicant> applicants;

    private Map<Tour, Set<Applicant>> applicantsInTour;

    @PostConstruct
    private void createApplicants() {
        applicants = new ArrayList<>();
        IntStream.rangeClosed(1, NUM_OF_APPLICANTS).forEach(i -> {
            applicants.add(RandomApplicantFactory.getRandomApplicant());
        });
        applicantsInTour = new HashMap<>();
    }

    public void addApplicantsInTour(Tour tour, Set<Applicant> applicants) {
        applicantsInTour.put(tour, applicants);
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }
}
