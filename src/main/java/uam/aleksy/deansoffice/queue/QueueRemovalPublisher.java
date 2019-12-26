package uam.aleksy.deansoffice.queue;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class QueueRemovalPublisher {

    private List<QueueRemovalListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void registerListener(QueueRemovalListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(Applicant applicant) {
        listeners.forEach(listener -> listener.onRemove(applicant));
    }
}
