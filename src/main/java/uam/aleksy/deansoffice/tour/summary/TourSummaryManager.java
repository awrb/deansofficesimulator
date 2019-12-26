package uam.aleksy.deansoffice.tour.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.tour.NextTourListener;
import uam.aleksy.deansoffice.tour.NextTourPublisher;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

@Component
public class TourSummaryManager implements NextTourListener {

    private TourSummaryRepository tourSummaryRepository;

    private TourSummaryFactory tourSummaryFactory;

    private NextTourPublisher nextTourPublisher;


    @Autowired
    public TourSummaryManager(TourSummaryRepository tourSummaryRepository,
                              TourSummaryFactory tourSummaryFactory,
                              NextTourPublisher publisher) {
        this.tourSummaryRepository = tourSummaryRepository;
        this.tourSummaryFactory = tourSummaryFactory;
        this.nextTourPublisher = publisher;
        nextTourPublisher.registerListener(this);
    }


    @Override
    public void nextTour(Tour tour) {
        TourSummary summary = tourSummaryFactory.createTourSummary(tour);
        tourSummaryRepository.add(summary);
        TourSummaryLogger.logTourSummary(summary);
    }
}
