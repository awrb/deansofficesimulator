package uam.aleksy.deansoffice.tour.consequences;

import lombok.extern.java.Log;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.consequences.data.*;

import java.util.List;
import java.util.stream.Collectors;

@Log
public class ConsequenceLogger {

    public static void reportConsequence(Consequence consequence) {

        String message = "Consequences for " + consequence.getApplicant().getName() + ": ";

        if (consequence.getClass().equals(StudentConsequences.class)) {
            message += "beers to drink: " + ((StudentConsequences) consequence).getBeersToDrink();
        } else if (consequence.getClass().equals(AdjunctConsequences.class)) {
            message += "extra tasks: " + ((AdjunctConsequences) consequence).getExtraTasks();
        } else if (consequence.getClass().equals(ProfessorConsequences.class)) {
            message += "extra differential degree: " + ((ProfessorConsequences) consequence).getDifferentialDegree();
        } else if (consequence.getClass().equals(DeanConsequences.class)) {
            List<Employee> firedEmployees = ((DeanConsequences) consequence).getFiredEmployees();
            String firedEmployeeNames = firedEmployees.stream()
                    .map(Employee::getName)
                    .collect(Collectors.joining("-", "{", "}"));
            message += "fired employees: " + firedEmployeeNames;
        } else if (consequence.getClass().equals(DoctoralStudentConsequences.class)) {
            List<Student> punishedStudents = ((DoctoralStudentConsequences) consequence).getPunishedStudents();
            String punishedStudentNames = punishedStudents.stream()
                    .map(Applicant::getName)
                    .collect(Collectors.joining("-", "{", "}"));
            message += "punished students: " + punishedStudentNames;
        } else { // acquaintance
            message += "minutes to complain: " + ((AcquaintanceConsequences) consequence).getMinutesComplained();
        }

        log.info(message);
    }
}
