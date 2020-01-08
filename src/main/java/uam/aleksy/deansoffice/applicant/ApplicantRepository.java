package uam.aleksy.deansoffice.applicant;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.utils.Constants;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ApplicantRepository {

    private List<Applicant> applicants;

    private AtomicLong idGenerator;

    @PostConstruct
    private void init() {
        applicants = new ArrayList<>();
        idGenerator = new AtomicLong(Constants.FIRST_ID);
    }

    public void add(Collection<Applicant> applicantsCollection) {
        applicantsCollection.forEach(applicant -> {
            applicant.setId(idGenerator.incrementAndGet());
            applicants.add(applicant);
        });
    }

    public Applicant add(Applicant applicant) {
        applicant.setId(idGenerator.incrementAndGet());
        applicants.add(applicant);
        return applicant;
    }


    public Optional<Applicant> getById(@NonNull Long id) {
        return applicants.stream().filter(applicant -> applicant.getId().equals(id)).findFirst();
    }
}
