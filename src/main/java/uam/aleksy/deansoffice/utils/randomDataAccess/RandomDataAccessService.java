package uam.aleksy.deansoffice.utils.randomDataAccess;

import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.queue.OfficeQueue;

import java.util.Optional;

@Service
public class RandomDataAccessService {

    private OfficeQueue officeQueue;

    public RandomDataAccessService(OfficeQueue officeQueue) {
        this.officeQueue = officeQueue;
    }

    public Optional<Student> getRandomStudent() {
        return officeQueue.getQueue()
                .stream()
                .filter(applicant -> applicant.getClass().equals(Student.class))
                .map(Student.class::cast)
                .findFirst();
    }
}