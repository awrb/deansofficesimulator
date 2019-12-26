package uam.aleksy.deansoffice.queue;

import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueueAdditionPublisher {

    private List<QueueAdditionListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void registerListener(QueueAdditionListener listener) {
        listeners.add(listener);
    }

    public void applicantAdded(Applicant applicant) {
        listeners.forEach(listener -> listener.applicantAdded(applicant));
    }

    public void applicantsAdded(List<Applicant> applicants) {
        listeners.forEach(listener -> listener.applicantsAdded(applicants));
    }
}
