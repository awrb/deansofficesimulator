package uam.aleksy.deansoffice.tour.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

import java.util.List;

@Component
public class TourSummaryManager {

    private TourSummaryRepository tourSummaryRepository;

    private TourSummaryFactory tourSummaryFactory;


    @Autowired
    public TourSummaryManager(TourSummaryRepository tourSummaryRepository, TourSummaryFactory tourSummaryFactory) {
        this.tourSummaryRepository = tourSummaryRepository;
        this.tourSummaryFactory = tourSummaryFactory;
    }

    public void summarizeTour(Tour tour, List<Consequence> consequences) {
        TourSummary summary = tourSummaryFactory.createTourSummary(tour, consequences);
        tourSummaryRepository.add(summary);
        TourSummaryLogger.logTourSummary(summary);
    }
}
