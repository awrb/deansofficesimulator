package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class QueueStateManager {

    private List<QueueStateListener> listeners;

    @PostConstruct
    private void init() {
        listeners = new ArrayList<>();
    }

    public void notifyNextRound() {
        log.info("New round starting");
        listeners.forEach(listener -> listener.nextRound());
    }

    public void registerListener(QueueStateListener listener) {
        listeners.add(listener);
    }
}