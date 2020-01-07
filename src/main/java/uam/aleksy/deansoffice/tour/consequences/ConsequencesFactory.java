package uam.aleksy.deansoffice.tour.consequences;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.consequences.data.*;
import uam.aleksy.deansoffice.utils.randomDataAccess.RandomDataAccessService;

import java.util.ArrayList;
import java.util.Optional;

@Log
@Component
public class ConsequencesFactory {

    private RandomDataAccessService randomDataAccessService;

    private EmployeeRepository employeeRepository;

    private ConsequenceRepository consequenceRepository;

    @Autowired
    public ConsequencesFactory(RandomDataAccessService randomDataAccessService,
                               EmployeeRepository employeeRepository,
                               ConsequenceRepository consequenceRepository) {
        this.randomDataAccessService = randomDataAccessService;
        this.employeeRepository = employeeRepository;
        this.consequenceRepository = consequenceRepository;
    }

    public ConsequenceCreatedPair createConsequence(Applicant applicant) {

        Class<? extends Applicant> clazz = applicant.getClass();

        Consequence existingConsequence = consequenceRepository.getConsequencesForApplicant(applicant);

        if (clazz.equals(Student.class)) {
            return createForStudent((Student) applicant, existingConsequence);
        } else if (clazz.equals(Professor.class)) {
            return createForProfessor((Professor) applicant, existingConsequence);
        } else if (clazz.equals(Adjunct.class)) {
            return createForAdjunct((Adjunct) applicant, existingConsequence);
        } else if (clazz.equals(DoctoralStudent.class)) {
            return createForDoctoralStudent((DoctoralStudent) applicant, existingConsequence);
        } else if (clazz.equals(Acquaintance.class)) {
            return createForAcquaintance((Acquaintance) applicant, existingConsequence);
        } else {
            return createForDean((Dean) applicant, existingConsequence);
        }

    }

    private ConsequenceCreatedPair createForStudent(Student student, Consequence existingConsequence) {
        int roundsWaited = student.getRoundsWaited();
        if ((roundsWaited > 0) && (roundsWaited % 2 == 0)) {
            if (existingConsequence != null) {
                ((StudentConsequences) existingConsequence).incrementBeersToDrink();
                return new ConsequenceCreatedPair(existingConsequence, false);
            }

            StudentConsequences studentConsequences = new StudentConsequences(student);
            studentConsequences.incrementBeersToDrink();
            return new ConsequenceCreatedPair(studentConsequences, true);
        }
        return null;
    }

    private ConsequenceCreatedPair createForProfessor(Professor professor, Consequence existingConsequence) {
        if (existingConsequence != null) {
            ((ProfessorConsequences) existingConsequence).incrementDifferentialDegree();
            return new ConsequenceCreatedPair(existingConsequence, false);
        }

        ProfessorConsequences professorConsequences = new ProfessorConsequences(professor);
        professorConsequences.incrementDifferentialDegree();
        return new ConsequenceCreatedPair(professorConsequences, true);
    }

    private ConsequenceCreatedPair createForAdjunct(Adjunct adjunct, Consequence existingConsequence) {
        if (existingConsequence != null) {
            ((AdjunctConsequences) existingConsequence).incrementExtraTasks();
            return new ConsequenceCreatedPair(existingConsequence, false);
        }
        AdjunctConsequences adjunctConsequences = new AdjunctConsequences(adjunct);
        adjunctConsequences.incrementExtraTasks();
        return new ConsequenceCreatedPair(adjunctConsequences, true);
    }

    private ConsequenceCreatedPair createForDoctoralStudent(DoctoralStudent doctoralStudent, Consequence existingConsequence) {
        Optional<Student> optionalStudent = randomDataAccessService.getRandomStudent();
        if (optionalStudent.isPresent()) {

            if (existingConsequence != null) {
                ((DoctoralStudentConsequences) existingConsequence).addStudent(optionalStudent.get());
                return new ConsequenceCreatedPair(existingConsequence, false);
            }

            DoctoralStudentConsequences doctoralStudentConsequences =
                    new DoctoralStudentConsequences(doctoralStudent, new ArrayList<>());
            doctoralStudentConsequences.addStudent(optionalStudent.get());
            return new ConsequenceCreatedPair(doctoralStudentConsequences, true);
        }
        return null;
    }

    private ConsequenceCreatedPair createForAcquaintance(Acquaintance acquaintance, Consequence existingConsequence) {

        if (existingConsequence != null) {
            ((AcquaintanceConsequences) existingConsequence).incrementMinutesComplained();
            return new ConsequenceCreatedPair(existingConsequence, false);
        }

        AcquaintanceConsequences acquaintanceConsequences = new AcquaintanceConsequences(acquaintance);
        acquaintanceConsequences.incrementMinutesComplained();
        return new ConsequenceCreatedPair(acquaintanceConsequences, true);
    }

    private ConsequenceCreatedPair createForDean(Dean dean, Consequence existingConsequence) {
        int roundsWaited = dean.getRoundsWaited();
        if (roundsWaited > 0) {
            Employee employee = employeeRepository.getApplicantsEmployee(dean);

            if (existingConsequence != null) {
                if (employee != null) {
                    ((DeanConsequences) existingConsequence).addEmployee(employee);
                }
                return new ConsequenceCreatedPair(existingConsequence, false);
            }

            DeanConsequences deanConsequences = new DeanConsequences(dean, new ArrayList<>());
            if (employee != null) {
                deanConsequences.addEmployee(employee);
            }
            return new ConsequenceCreatedPair(deanConsequences, true);
        }

        // no consequences because dean hasn't waited long enough to get angry
        return null;
    }
}
