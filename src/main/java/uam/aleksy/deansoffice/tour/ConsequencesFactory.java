package uam.aleksy.deansoffice.tour;

import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.tour.data.Consequence;
import uam.aleksy.deansoffice.tour.enums.ConsequenceType;

class ConsequencesFactory {

    static Consequence createConsequence(Applicant applicant) {

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
            consequence.setConsequenceType(ConsequenceType.PUNISH_STUDENT);
            consequence.setConsequenceValue("PLACEHOLDER");
        } else if (clazz.equals(Acquaintance.class)){
            consequence.setConsequenceType(ConsequenceType.COMPLAIN);
            consequence.setConsequenceValue(((Acquaintance) applicant).getMinutesComplained());
        } else { // TODO DEAN
            consequence.setConsequenceType(ConsequenceType.FIRE_EMPLOYEE);
            consequence.setConsequenceValue("PLACEHOLDER");
        }

        return consequence;
    }
}
