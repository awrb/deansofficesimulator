package uam.aleksy.deansoffice.simulation;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Log
@Component
public class SimulationRunner implements CommandLineRunner {

    private SimulationStartPublisher simulationStartPublisher;

    private SimulationEngine engine;

    @Autowired
    public SimulationRunner(SimulationStartPublisher simulationStartPublisher, SimulationEngine engine) {
        this.simulationStartPublisher = simulationStartPublisher;
        this.engine = engine;
    }

    @Override
    public void run(String... args) {

        simulationStartPublisher.notifyStart();
//        engine.runSimulation();
    }
}
