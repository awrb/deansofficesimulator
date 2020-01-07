package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceLogger;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository;
import uam.aleksy.deansoffice.tour.consequences.ConsequencesFactory;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.consequences.data.ConsequenceCreatedPair;
import uam.aleksy.deansoffice.tour.consequences.data.DeanConsequences;
import uam.aleksy.deansoffice.tour.data.Tour;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Tracks consequences for all applicants in the queue after every tour
 */
@Component
@Log
public class TourConsequencesTracker implements NextTourListener {

    private TourRepository tourRepository;

    private NextTourPublisher publisher;

    private ConsequenceRepository consequenceRepository;

    private ConsequencesFactory consequencesFactory;

    private EmployeeRepository employeeRepository;

    private OfficeQueue officeQueue;


    @Autowired
    public TourConsequencesTracker(TourRepository tourRepository,
                                   NextTourPublisher publisher,
                                   EmployeeRepository employeeRepository,
                                   ConsequencesFactory consequencesFactory,
                                   ConsequenceRepository consequenceRepository,
                                   OfficeQueue officeQueue) {
        this.tourRepository = tourRepository;
        this.publisher = publisher;
        this.consequencesFactory = consequencesFactory;
        this.consequenceRepository = consequenceRepository;
        this.employeeRepository = employeeRepository;
        this.officeQueue = officeQueue;
    }


    @PostConstruct
    private void register() {
        publisher.registerListener(this);
    }


    @Override
    public void nextTour(Tour tour) {
        List<Consequence> consequencesOfTour = new ArrayList<>();

        // increment rounds waited for everyone in the queue
        officeQueue.getQueue().forEach(Applicant::incrementRoundsWaited);

        Set<Applicant> applicants = tourRepository.getLastTourApplicants();

        applicants.forEach(applicant -> {
            ConsequenceCreatedPair consequenceCreatedPair = consequencesFactory.createConsequence(applicant);
            applicant.incrementRoundsWaited();

            // due to probabilistic circumstances, consequence was null
            if (consequenceCreatedPair != null) {
                Consequence consequence = consequenceCreatedPair.getConsequence();
                consequencesOfTour.add(consequence);
                ConsequenceLogger.reportConsequence(consequence);

                // if the consequence was created, add it to the repository
                if (consequenceCreatedPair.isCreated()) {
                    consequenceRepository.addConsequenceForApplicant(applicant, consequence);
                }

                // for DeanConsequences, apply the side effect of firing the employee
                if (consequence.getClass().equals(DeanConsequences.class)) {
                    employeeRepository.removeEmployee(employeeRepository.getApplicantsEmployee(applicant));
                }

            }
        });

        tour.setConsequences(consequencesOfTour);
    }
}
