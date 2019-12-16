package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class SimulationStatePublisher {

    private List<SimulationStateListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void notifyNextTour() {
        log.info("New tour starting");
        listeners.forEach(SimulationStateListener::nextTour);
    }

    public void notifyStart() {
        log.info("Queue simulation starting");
        listeners.forEach(SimulationStateListener::onStart);
    }

    public void registerListener(SimulationStateListener listener) {
        listeners.add(listener);
    }
}
