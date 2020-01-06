package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceLogger;
import uam.aleksy.deansoffice.tour.consequences.ConsequencesFactory;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.utils.randomDataAccess.RandomDataAccessService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Log
public class TourConsequencesTracker implements NextTourListener {

    private TourRepository tourRepository;

    private RandomDataAccessService randomDataAccessService;

    private NextTourPublisher publisher;

    private EmployeeRepository employeeRepository;

    private ConsequencesFactory consequencesFactory;


    @Autowired
    public TourConsequencesTracker(TourRepository tourRepository,
                                   RandomDataAccessService randomDataAccessService,
                                   NextTourPublisher publisher,
                                   EmployeeRepository employeeRepository,
                                   ConsequencesFactory consequencesFactory) {
        this.tourRepository = tourRepository;
        this.randomDataAccessService = randomDataAccessService;
        this.publisher = publisher;
        this.employeeRepository = employeeRepository;
        this.consequencesFactory = consequencesFactory;
    }


    @PostConstruct
    private void register() {
        publisher.registerListener(this);
    }


    @Override
    public void nextTour(Tour tour) {
        List<Consequence> consequencesOfTour = new ArrayList<>();

        Set<Applicant> applicants = tourRepository.getLastTourApplicants();

        applicants.forEach(applicant -> {
            applicant.incrementRoundsWaited();
            Consequence consequence = consequencesFactory.createConsequence(applicant);

            // due to circumstances, consequence was null...
            if (consequence != null) {
                consequencesOfTour.add(consequence);
                ConsequenceLogger.reportConsequence(consequence);
            }
        });

        tour.setConsequences(consequencesOfTour);
    }
}
