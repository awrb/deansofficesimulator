package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.queue.QueueRemovalListener;
import uam.aleksy.deansoffice.queue.QueueRemovalPublisher;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@Log
public class TourManager implements QueueRemovalListener {

    private TourRepository tourRepository;

    private NextTourPublisher nextTourPublisher;

    private QueueRemovalPublisher removalPublisher;

    private Set<Applicant> applicantsForTour;

    @Autowired
    public TourManager(TourRepository tourRepository, NextTourPublisher nextTourPublisher,
                       QueueRemovalPublisher removalPublisher) {
        this.tourRepository = tourRepository;
        this.nextTourPublisher = nextTourPublisher;
        this.removalPublisher = removalPublisher;
    }

    @PostConstruct
    private void register() {
        removalPublisher.registerListener(this);

        applicantsForTour = new HashSet<>();
    }

    @Override
    public void onRemove(Applicant applicant) {
        addApplicant(applicant);
    }

    public void addApplicant(Applicant applicant) {
        applicantsForTour.add(applicant);
    }


    public void finishRound() {
        Tour newTour = tourRepository.addNewTour(applicantsForTour);
        nextTourPublisher.notifyNextTour(newTour);
    }
}
