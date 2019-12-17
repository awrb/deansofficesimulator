package uam.aleksy.deansoffice.tour;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NextTourPublisher {

    private List<NextTourListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void notifyNextTour() {
        listeners.forEach(NextTourListener::nextTour);
    }

    public void registerListener(NextTourListener listener) {
        this.listeners.add(listener);
    }
}
