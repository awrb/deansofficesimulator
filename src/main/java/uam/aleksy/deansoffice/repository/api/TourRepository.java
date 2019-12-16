package uam.aleksy.deansoffice.repository.api;

import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Tour;

import java.util.List;
import java.util.Map;

public interface TourRepository {

    void addNewTour(Tour tour, List<Applicant> applicantList);

    Map<Tour, List<Applicant>> getTourApplicantsMap();

    Tour getLastTour();
}
