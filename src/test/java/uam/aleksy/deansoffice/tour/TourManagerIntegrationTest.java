package uam.aleksy.deansoffice.tour;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Dean;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@SpringBootTest
public class TourManagerIntegrationTest {

    @Autowired
    private TourManager tourManager;

    @Autowired
    private OfficeQueue officeQueue;

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void shouldAddToTourRepositoryOnFinishRound() {

        // given
        Applicant student = new Student();
        Applicant dean = new Dean();

        // when
        tourManager.addApplicant(student);

        officeQueue.add(dean);
        // remove() triggers onRemove listener method which should call addApplicant
        officeQueue.remove();

        tourManager.finishRound();

        // then

        // end up with two applicants in tour, one from calling addApplicant and one from popping the queue
        Assertions.assertEquals(tourRepository.getLastTourApplicants().size(), 2);
    }

}
