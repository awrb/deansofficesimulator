package uam.aleksy.deansoffice.utils.randomDataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Student;

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