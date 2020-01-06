package uam.aleksy.deansoffice.tour.consequences;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ConsequenceRepository {

    private Map<Applicant, Consequence> applicantConsequenceMap;

    @PostConstruct
    private void init() {
        applicantConsequenceMap = new HashMap<>();
    }

    public void addConsequenceForApplicant(Applicant applicant, Consequence consequence) {
        applicantConsequenceMap.put(applicant, consequence);
    }

    public Consequence getConsequencesForApplicant(Applicant applicant) {
        return applicantConsequenceMap.get(applicant);
    }
}
