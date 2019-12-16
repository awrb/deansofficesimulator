package uam.aleksy.deansoffice.utils.randomDataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Student;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;

import java.util.Optional;

@Service
public class RandomDataAccessService {

    private ApplicantRepository applicantRepository;

    @Autowired
    public RandomDataAccessService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public Optional<Student> getRandomStudent() {
        return applicantRepository.getApplicants()
                .stream()
                .filter(applicant -> applicant.getClass().equals(Student.class))
                .map(Student.class::cast)
                .findFirst();
    }
}