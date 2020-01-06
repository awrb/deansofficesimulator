package uam.aleksy.deansoffice.tour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.applicant.data.Professor;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.data.Tour;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TourConsequencesTrackerIntegrationTest {

    @Autowired
    private TourManager tourManager;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TourConsequencesTracker tourConsequencesTracker;


    @Test
    public void testConsequenceTracking() {
        // given
        Professor professor = new Professor();

        employeeRepository.assignEmployeeToApplicant(new Employee(), professor);
        tourManager.addApplicant(professor);

        // when
        tourManager.finishRound();
        List<Tour> tours = new ArrayList<>(tourRepository.getTourApplicantsMap().keySet());
        Tour tour = tours.get(tours.size() - 1);

        // then

        // consequences get created after finishRound is called thanks to TourConsequencesTracker listening
        Assertions.assertFalse(tour.getConsequences().isEmpty());
    }
}
