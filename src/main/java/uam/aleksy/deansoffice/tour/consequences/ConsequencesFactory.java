package uam.aleksy.deansoffice.tour.consequences;

import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.consequences.data.ConsequenceContext;
import uam.aleksy.deansoffice.tour.consequences.enums.ConsequenceType;

import java.util.Optional;

public class ConsequencesFactory {

    public static Consequence createConsequence(Applicant applicant, ConsequenceContext consequenceContext) {

        Class<? extends Applicant> clazz = applicant.getClass();

        Consequence consequence = new Consequence();

        consequence.setApplicantName(applicant.getName());

        if (clazz.equals(Student.class)) {
            consequence.setConsequenceType(ConsequenceType.BEER);
            consequence.setConsequenceValue(((Student) applicant).getBeersToDrink());
        } else if (clazz.equals(Professor.class)) {
            consequence.setConsequenceType(ConsequenceType.EXTRA_DIFFERENTIAL);
            consequence.setConsequenceValue(((Professor) applicant).getDifferentialDegree());
        } else if (clazz.equals(Adjunct.class)) {
            consequence.setConsequenceType(ConsequenceType.EXTRA_TASKS);
            consequence.setConsequenceValue(((Adjunct) applicant).getExtraTasks());
        } else if (clazz.equals(DoctoralStudent.class)) {

            if (consequenceContext == null) {
                // no student was punished, there was no consequence
                return null;
            }

            consequence.setConsequenceType(ConsequenceType.PUNISH_STUDENT);

            Optional<Student> optionalStudent = consequenceContext.getPunishedStudent();

            String consequenceValue = optionalStudent.isPresent() ? optionalStudent.get().getName() : "NO STUDENT TO PUNISH";
            consequence.setConsequenceValue(consequenceValue);

        } else if (clazz.equals(Acquaintance.class)) {
            consequence.setConsequenceType(ConsequenceType.COMPLAIN);
            consequence.setConsequenceValue(((Acquaintance) applicant).getMinutesComplained());
        } else { // TODO DEAN
            consequence.setConsequenceType(ConsequenceType.FIRE_EMPLOYEE);
            consequence.setConsequenceValue("PLACEHOLDER");
        }

        return consequence;
    }
}
