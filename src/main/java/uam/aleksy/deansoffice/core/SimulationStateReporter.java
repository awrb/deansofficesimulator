package uam.aleksy.deansoffice.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Tour;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;
import uam.aleksy.deansoffice.repository.api.TourRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Log
public class SimulationStateReporter implements SimulationStateListener {

    private ApplicantRepository applicantRepository;

    private TourRepository tourRepository;

    private SimulationStatePublisher simulationStatePublisher;

    @Autowired
    public SimulationStateReporter(ApplicantRepository applicantRepository, TourRepository tourRepository,
                                   SimulationStatePublisher simulationStatePublisher) {
        this.applicantRepository = applicantRepository;
        this.tourRepository = tourRepository;
        this.simulationStatePublisher = simulationStatePublisher;
    }

    @PostConstruct
    private void register() {
        simulationStatePublisher.registerListener(this);
    }

    @Override
    public void nextTour() {
//        Tour tour = tourRepository.getLastTour();
//        List<Applicant> applicants = applicantRepository.getApplicantsByTour(tour);
//
        List<Applicant> applicants = applicantRepository.getApplicants();
        applicants.forEach(this::reportStateForApplicant);
    }

    @Override
    public void onStart() {
    }

    private void reportStateForApplicant(Applicant applicant) {
        log.info("TODO REPORT STATE");
    }


}
