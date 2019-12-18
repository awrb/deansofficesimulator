package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceLogger;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository;
import uam.aleksy.deansoffice.tour.consequences.ConsequencesFactory;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.consequences.data.ConsequenceContext;
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

    private EmployeeRepository employeeRepository;


    @Autowired
    public TourConsequencesTracker(TourRepository tourRepository,
                                   RandomDataAccessService randomDataAccessService,
                                   ConsequenceRepository consequenceRepository,
                                   NextTourPublisher publisher,
                                   TourSummaryManager tourSummaryManager,
                                   EmployeeRepository employeeRepository) {
        this.tourRepository = tourRepository;
        this.randomDataAccessService = randomDataAccessService;
        this.consequenceRepository = consequenceRepository;
        this.publisher = publisher;
        this.tourSummaryManager = tourSummaryManager;
        this.employeeRepository = employeeRepository;
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
            ConsequenceContext consequenceContext = applyConsequencesForApplicant(applicant);
            Consequence consequence = ConsequencesFactory.createConsequence(applicant, consequenceContext);

            // due to circumstances, consequence was null...
            if (consequence != null) {
                consequencesOfTour.add(consequence);
                ConsequenceLogger.reportConsequence(consequence);
            }
        });

        consequenceRepository.add(tour, consequencesOfTour);

        // TODO słabe miejsce na to, wywołanie tutaj by na pewno najpierw stworzono konsekwencje
        // a dopiero potem podsumowanie
        tourSummaryManager.summarizeTour(tour, consequencesOfTour);

    }

    private ConsequenceContext applyConsequencesForApplicant(Applicant applicant) {
        Class<? extends Applicant> applicantClazz = applicant.getClass();

        ConsequenceContext consequenceContext = null;

        int roundsWaited = applicant.getRoundsWaited();

        Predicate<Integer> everyTwoRounds = rounds -> rounds > 0 && rounds % 2 == 0;

        if (applicantClazz.equals(Student.class)) {
            if (everyTwoRounds.test(roundsWaited)) {
                ((Student) applicant).incrementBeersToDrink();
            }
        } else if (applicantClazz.equals(Dean.class)) {
            // TODO poprawa logiki i magic number
            if (roundsWaited > 0) {
                // in other words, fire him
                employeeRepository.removeEmployeeByApplicant(applicant);
            }
        } else if (applicantClazz.equals(Professor.class)) {
            ((Professor) applicant).incrementDifferentialDegree();
        } else if (applicantClazz.equals(Adjunct.class)) {
            ((Adjunct) applicant).incrementExtraTasks();
        } else if (applicantClazz.equals(Acquaintance.class)) {
            ((Acquaintance) applicant).incrementMinutesComplained();
        } else {
            // last case - DoctoralStudent punishes a random student
            if (everyTwoRounds.test(roundsWaited)) {
                consequenceContext = new ConsequenceContext();
                Optional<Student> optionalStudent = randomDataAccessService.getRandomStudent();
                optionalStudent.ifPresent(Student::incrementMarkPunishment);
                consequenceContext.setPunishedStudent(optionalStudent);
            }
        }

        return consequenceContext;
    }
}
