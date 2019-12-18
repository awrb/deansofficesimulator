package uam.aleksy.deansoffice.tour.consequences;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ConsequenceRepository {

    private Map<Tour, List<Consequence>> tourConsequences;

    @PostConstruct
    private void init() {
        tourConsequences = new HashMap<>();
    }

    public void add(Tour tour, List<Consequence> consequences) {
        tourConsequences.put(tour, consequences);
    }

    public List<Consequence> getConsequencesForTour(Tour tour) {
        return tourConsequences.get(tour);
    }
}
