package uam.aleksy.deansoffice.applicant;


import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Repository
public class ApplicantRepository {

    private static final int NUM_OF_APPLICANTS = 100;

    private List<Applicant> applicants;

    @PostConstruct
    private void createApplicants() {
        applicants = new ArrayList<>();
        IntStream.rangeClosed(1, NUM_OF_APPLICANTS).forEach(i -> {
            applicants.add(RandomApplicantFactory.getRandomApplicant());
        });
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }
}
