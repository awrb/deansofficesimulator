package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceLogger;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository;
import uam.aleksy.deansoffice.tour.consequences.ConsequencesFactory;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.data.Tour;
import uam.aleksy.deansoffice.tour.summary.TourSummaryManager;
import uam.aleksy.deansoffice.utils.randomDataAccess.RandomDataAccessService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Component
@Log
public class TourConsequencesTracker implements NextTourListener {

    private TourRepository tourRepository;

    private RandomDataAccessService randomDataAccessService;

    private ConsequenceRepository consequenceRepository;

    private NextTourPublisher publisher;

    private TourSummaryManager tourSummaryManager;


    @Autowired
    public TourConsequencesTracker(TourRepository tourRepository,
                                   RandomDataAccessService randomDataAccessService,
                                   ConsequenceRepository consequenceRepository,
                                   NextTourPublisher publisher,
                                   TourSummaryManager tourSummaryManager) {
        this.tourRepository = tourRepository;
        this.randomDataAccessService = randomDataAccessService;
        this.consequenceRepository = consequenceRepository;
        this.publisher = publisher;
        this.tourSummaryManager = tourSummaryManager;
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
            applyConsequencesForApplicant(applicant);
            Consequence consequence = ConsequencesFactory.createConsequence(applicant);
            consequencesOfTour.add(consequence);
            ConsequenceLogger.reportConsequence(consequence);
        });

        consequenceRepository.add(tour, consequencesOfTour);

        // TODO słabe miejsce na to, wywołanie tutaj by na pewno najpierw stworzono konsekwencje
        // a dopiero potem podsumowanie
        tourSummaryManager.summarizeTour(tour, consequencesOfTour);

    }

    private void applyConsequencesForApplicant(Applicant applicant) {
        Class<? extends Applicant> applicantClazz = applicant.getClass();

        int roundsWaited = applicant.getRoundsWaited();

        Predicate<Integer> everyTwoRounds = rounds -> rounds > 0 && rounds % 2 == 0;

        if (applicantClazz.equals(Student.class)) {
            if (everyTwoRounds.test(roundsWaited)) {
                ((Student) applicant).incrementBeersToDrink();
            }
//      } TODO LOGIC
//        } else if (applicantClazz.equals(Dean.class)) {
//            if (roundsWaited == 4) {
//                Employee employeeToFire = employeeRepository.getApplicantsEmployee(applicant);
//                employeeManagementService.fireEmployee(employeeToFire);
//            }
        } else if (applicantClazz.equals(Professor.class)) {
            ((Professor) applicant).incrementDifferentialDegree();
        } else if (applicantClazz.equals(Adjunct.class)) {
            ((Adjunct) applicant).incrementExtraTasks();
        } else if (applicantClazz.equals(Acquaintance.class)) {
            ((Acquaintance) applicant).incrementMinutesComplained();
        } else {
            // last case - DoctoralStudent punishes a random student
            if (everyTwoRounds.test(roundsWaited)) {
                Optional<Student> optionalStudent = randomDataAccessService.getRandomStudent();
                optionalStudent.ifPresent(Student::incrementMarkPunishment);
            }
        }
    }
}
