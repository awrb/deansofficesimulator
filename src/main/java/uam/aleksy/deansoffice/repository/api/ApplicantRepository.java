package uam.aleksy.deansoffice.repository.api;

import uam.aleksy.deansoffice.data.Applicant;

import java.util.List;

public interface ApplicantRepository {
    List<Applicant> getApplicants();
}
