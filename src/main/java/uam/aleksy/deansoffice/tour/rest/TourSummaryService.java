package uam.aleksy.deansoffice.tour.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uam.aleksy.deansoffice.tour.summary.TourSummaryRepository;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

import java.util.List;

@RestController
@RequestMapping("tour")
public class TourSummaryService {

    private TourSummaryRepository tourSummaryRepository;

    @Autowired
    public TourSummaryService(TourSummaryRepository tourSummaryRepository) {
        this.tourSummaryRepository = tourSummaryRepository;
    }

    /**
     * Can be used to monitor tour summaries in runtime
     * @return list of tour summaries
     */
    @GetMapping
    public List<TourSummary> getTourSummaries() {
        return tourSummaryRepository.getSummaries();
    }
}
