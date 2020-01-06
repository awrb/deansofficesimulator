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
import java.util.function.Predicate;

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

    public Consequence createConsequence(Applicant applicant) {

        Class<? extends Applicant> clazz = applicant.getClass();

        int roundsWaited = applicant.getRoundsWaited();

        Consequence existingConsequence = consequenceRepository.getConsequencesForApplicant(applicant);

        Predicate<Integer> everyTwoRounds = rounds -> rounds > 0 && rounds % 2 == 0;

        if (clazz.equals(Student.class)) {
            if (everyTwoRounds.test(roundsWaited)) {
                if (existingConsequence != null) {
                    ((StudentConsequences) existingConsequence).incrementBeersToDrink();
                    return existingConsequence;
                }

                StudentConsequences studentConsequences = new StudentConsequences(applicant);
                studentConsequences.incrementBeersToDrink();
                consequenceRepository.addConsequenceForApplicant(applicant, studentConsequences);
                return studentConsequences;
            }
        } else if (clazz.equals(Professor.class)) {

            if (existingConsequence != null) {
                ((ProfessorConsequences) existingConsequence).incrementDifferentialDegree();
                return existingConsequence;
            }

            ProfessorConsequences professorConsequences = new ProfessorConsequences(applicant);
            professorConsequences.incrementDifferentialDegree();
            consequenceRepository.addConsequenceForApplicant(applicant, professorConsequences);
            return professorConsequences;
        } else if (clazz.equals(Adjunct.class)) {
            if (existingConsequence != null) {
                ((AdjunctConsequences) existingConsequence).incrementExtraTasks();
                return existingConsequence;
            }
            AdjunctConsequences adjunctConsequences = new AdjunctConsequences(applicant);
            adjunctConsequences.incrementExtraTasks();
            consequenceRepository.addConsequenceForApplicant(applicant, adjunctConsequences);
            return adjunctConsequences;
        } else if (clazz.equals(DoctoralStudent.class)) {

            Optional<Student> optionalStudent = randomDataAccessService.getRandomStudent();
            if (optionalStudent.isPresent()) {

                if (existingConsequence != null) {
                    ((DoctoralStudentConsequences) existingConsequence).addStudent(optionalStudent.get());
                    return existingConsequence;
                }

                DoctoralStudentConsequences doctoralStudentConsequences =
                        new DoctoralStudentConsequences(applicant, new ArrayList<>());
                doctoralStudentConsequences.addStudent(optionalStudent.get());
                consequenceRepository.addConsequenceForApplicant(applicant, doctoralStudentConsequences);
                return doctoralStudentConsequences;
            }

        } else if (clazz.equals(Acquaintance.class)) {

            if (existingConsequence != null) {
                ((AcquaintanceConsequences) existingConsequence).incrementMinutesComplained();
                return existingConsequence;
            }

            AcquaintanceConsequences acquaintanceConsequences = new AcquaintanceConsequences(applicant);
            acquaintanceConsequences.incrementMinutesComplained();
            consequenceRepository.addConsequenceForApplicant(applicant, acquaintanceConsequences);
            return acquaintanceConsequences;
        } else {
            if (roundsWaited > 0) {

                // fire him
                Employee employee = employeeRepository.getApplicantsEmployee(applicant);
                employeeRepository.removeEmployee(employee);

                if (existingConsequence != null) {
                    if (employee != null) {
                        ((DeanConsequences) existingConsequence).addEmployee(employee);
                    }
                    return existingConsequence;
                }

                DeanConsequences deanConsequences = new DeanConsequences(applicant, new ArrayList<>());
                if (employee != null) {
                    deanConsequences.addEmployee(employee);
                }
                consequenceRepository.addConsequenceForApplicant(applicant, deanConsequences);
                return deanConsequences;
            }

            // no consequences because dean hasn't waited long enough to get angry
            return null;
        }

        return null;
    }
}
