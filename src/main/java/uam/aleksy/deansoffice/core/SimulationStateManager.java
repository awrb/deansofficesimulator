package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class SimulationStateManager {

    private List<SimulationStateListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void notifyNextRound() {
        log.info("New round starting");
        listeners.forEach(listener -> listener.nextRound());
    }

    public void notifyStart() {
        log.info("Queue simulation starting");
        listeners.forEach(listeners -> listeners.onStart());
    }

    public void registerListener(SimulationStateListener listener) {
        listeners.add(listener);
    }
}
