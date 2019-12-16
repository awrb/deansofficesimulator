package uam.aleksy.deansoffice.repository.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Tour;

import java.util.List;

public interface ApplicantRepository {
    List<Applicant> getApplicants();

    List<Applicant> getApplicantsByTour(Tour tour);
}
