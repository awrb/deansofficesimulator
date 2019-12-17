package uam.aleksy.deansoffice.tour;

import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.tour.data.Consequence;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ConsequenceRepository {

    private List<Consequence> consequences;

    @PostConstruct
    private void init() {
        consequences = new ArrayList<>();
    }

    public void add(Consequence consequence) {
        consequences.add(consequence);
    }
}
