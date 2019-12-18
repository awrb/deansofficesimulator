package uam.aleksy.deansoffice.tour;

import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.tour.data.Tour;

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

    public void notifyNextTour(Tour tour) {
        listeners.forEach(listener -> listener.nextTour(tour));
    }

    public void registerListener(NextTourListener listener) {
        this.listeners.add(listener);
    }
}
