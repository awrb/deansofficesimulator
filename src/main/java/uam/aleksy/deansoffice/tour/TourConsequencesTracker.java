package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.tour.data.Consequence;
import uam.aleksy.deansoffice.utils.randomDataAccess.RandomDataAccessService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Component
@Log
public class TourConsequencesTracker implements NextTourListener {

    private ApplicantRepository applicantRepository;

    private TourRepository tourRepository;

    private RandomDataAccessService randomDataAccessService;

    private ConsequenceRepository consequenceRepository;

    private NextTourPublisher publisher;

    @Autowired
    public TourConsequencesTracker(ApplicantRepository applicantRepository, TourRepository tourRepository,
                                   RandomDataAccessService randomDataAccessService,
                                   ConsequenceRepository consequenceRepository,
                                   NextTourPublisher publisher) {
        this.applicantRepository = applicantRepository;
        this.tourRepository = tourRepository;
        this.randomDataAccessService = randomDataAccessService;
        this.consequenceRepository = consequenceRepository;
        this.publisher = publisher;
    }

    @PostConstruct
    private void register() {
        publisher.registerListener(this);
    }


    @Override
    public void nextTour() {
        Set<Applicant> applicants = tourRepository.getLastTourApplicants();
        applicants.forEach(this::applyTourConsequences);
    }

    private void applyTourConsequences(Applicant applicant) {
        applicant.incrementRoundsWaited();
        applyConsequencesForApplicant(applicant);
        Consequence consequence = ConsequencesFactory.createConsequence(applicant);
        consequenceRepository.add(consequence);
        ConsequenceLogger.reportConsequence(consequence);
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
