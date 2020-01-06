package uam.aleksy.deansoffice.tour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.tour.data.Tour;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TourRepositoryTest {

    @Test
    public void testAddNewTour() {

        // given
        TourRepository tourRepository = getRepository();

        // when
        Tour tour1 = tourRepository.addNewTour(new HashSet<>());
        Tour tour2 = tourRepository.addNewTour(new HashSet<>());

        // then
        Assertions.assertEquals(tour1.getId(), 1);
        Assertions.assertEquals(tour2.getId(), 2);

    }

    @Test
    public void testGetTourApplicantsMap() {

        // given
        TourRepository tourRepository = getRepository();

        Set<Applicant> applicants = Collections.singleton(new Student());
        Map<Tour, Set<Applicant>> tourApplicantMap = tourRepository.getTourApplicantsMap();

        // when
        Tour tour1 = tourRepository.addNewTour(applicants);

        // then
        Assertions.assertEquals(tourApplicantMap.get(tour1), applicants);

    }

    @Test
    public void testGetLastTourApplicants() {
        // given
        TourRepository tourRepository = getRepository();

        Set<Applicant> applicants = Collections.singleton(new Student());

        // when
        tourRepository.addNewTour(applicants);
        Set<Applicant> lastTourApplicants = tourRepository.getLastTourApplicants();

        // then
        Assertions.assertEquals(lastTourApplicants, applicants);
    }


    private TourRepository getRepository() {
        TourRepository tourRepository = new TourRepository();
        ReflectionTestUtils.invokeMethod(tourRepository, "init", (Object[]) null);
        return tourRepository;
    }
}
