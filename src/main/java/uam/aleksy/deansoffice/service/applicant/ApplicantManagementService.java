package uam.aleksy.deansoffice.service.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import uam.aleksy.deansoffice.core.SimulationStateListener;
import uam.aleksy.deansoffice.core.SimulationStatePublisher;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;

import javax.annotation.PostConstruct;

public class ApplicantManagementService implements SimulationStateListener {

    private ApplicantRepository applicantRepository;

    private SimulationStatePublisher simulationStatePublisher;

    public ApplicantManagementService(ApplicantRepository applicantRepository,
                                      SimulationStatePublisher simulationStatePublisher) {
        this.applicantRepository = applicantRepository;
        this.simulationStatePublisher = simulationStatePublisher;
    }

    @PostConstruct
    private void register() {
        simulationStatePublisher.registerListener(this);
    }

    @Override
    public void nextTour() {
        applicantRepository.getApplicants().forEach(Applicant::incrementRoundsWaited);
    }

    @Override
    public void onStart() {
        applicantRepository.getApplicants().forEach(applicant -> applicant.setRoundsWaited(0));
    }
}
