package uam.aleksy.deansoffice.simulation;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class SimulationStartPublisher {

    private List<SimulationStateListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void notifyStart() {
        log.info("Queue simulation starting");
        listeners.forEach(SimulationStateListener::onStart);
    }

    public void registerListener(SimulationStateListener listener) {
        listeners.add(listener);
    }
}
