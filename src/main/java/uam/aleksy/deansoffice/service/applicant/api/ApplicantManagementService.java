package uam.aleksy.deansoffice.service.applicant.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Task;

import java.util.List;

public interface ApplicantManagementService {

    List<Applicant> getApplicants();

    void removeTask(Applicant applicant);

    Task getNextTask(Applicant applicant);
}
