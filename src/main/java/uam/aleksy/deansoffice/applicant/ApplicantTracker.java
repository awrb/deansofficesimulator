package uam.aleksy.deansoffice.applicant;

import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.queue.QueueAdditionListener;
import uam.aleksy.deansoffice.queue.QueueAdditionPublisher;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class ApplicantTracker implements QueueAdditionListener {

    private ApplicantRepository applicantRepository;

    private QueueAdditionPublisher queueAdditionPublisher;

    public ApplicantTracker(ApplicantRepository applicantRepository, QueueAdditionPublisher queueAdditionPublisher) {
        this.applicantRepository = applicantRepository;
        this.queueAdditionPublisher = queueAdditionPublisher;
    }

    @PostConstruct
    private void register() {
        queueAdditionPublisher.registerListener(this);
    }

    @Override
    public void applicantAdded(Applicant applicant) {
        applicantRepository.add(Collections.singletonList(applicant));
    }

    @Override
    public void applicantsAdded(List<Applicant> applicants) {
        applicantRepository.add(applicants);
    }
}
