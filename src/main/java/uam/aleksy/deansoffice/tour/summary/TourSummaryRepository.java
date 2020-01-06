package uam.aleksy.deansoffice.tour.summary;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.tour.summary.data.TourSummary;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TourSummaryRepository {

    private List<TourSummary> summaries;

    @PostConstruct
    private void init() {
        summaries = new ArrayList<>();
    }

    public List<TourSummary> getSummaries() {
        return summaries;
    }

    public void add(TourSummary tourSummary) {
        summaries.add(tourSummary);
    }

}
