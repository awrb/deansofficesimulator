package uam.aleksy.deansoffice.service.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.data.*;
import uam.aleksy.deansoffice.repository.api.EmployeeRepository;
import uam.aleksy.deansoffice.service.employee.api.EmployeeManagementService;
import uam.aleksy.deansoffice.utils.randomDataAccess.RandomDataAccessService;

import java.util.Optional;

@Component
public class TourConsequencesTracker {

    private EmployeeManagementService employeeManagementService;

    private EmployeeRepository employeeRepository;

    private RandomDataAccessService randomDataAccessService;

    @Autowired
    public TourConsequencesTracker(EmployeeManagementService employeeManagementService,
                                   EmployeeRepository employeeRepository,
                                   RandomDataAccessService randomDataAccessService) {
        this.employeeManagementService = employeeManagementService;
        this.employeeRepository = employeeRepository;
        this.randomDataAccessService = randomDataAccessService;
    }

    void triggerSideEffects(Applicant applicant) {

        Class<? extends Applicant> applicantClazz = applicant.getClass();

        int roundsWaited = applicant.getRoundsWaited();

        if (applicantClazz.equals(Student.class)) {
            if (roundsWaited > 0 && roundsWaited % 2 == 0) {
                ((Student) applicant).incrementBeersToDrink();
            }
//      } TODO LOGIC
//        } else if (applicantClazz.equals(Dean.class)) {
//            if (roundsWaited == 4) {
//                Employee employeeToFire = employeeRepository.getApplicantsEmployee(applicant);
//                employeeManagementService.fireEmployee(employeeToFire);
//            }
        } else if (applicantClazz.equals(Professor.class)) {
            ((Professor) applicant).incrementDifferentialDegree();
        } else if (applicantClazz.equals(Adjunct.class)) {
            ((Adjunct) applicant).incrementExtraTasks();
        } else if (applicantClazz.equals(Acquaintance.class)) {
            applicant.incrementMinutesComplained();
        } else {
            // last case - DoctoralStudent punishes a random student
            if (roundsWaited > 0 && roundsWaited % 2 == 0) {
                Optional<Student> optionalStudent = randomDataAccessService.getRandomStudent();
                optionalStudent.ifPresent(Student::incrementMarkPunishment);
            }
        }
    }

    public TourConsequences createConsequences() {
        return null; // TODO
    }
}
