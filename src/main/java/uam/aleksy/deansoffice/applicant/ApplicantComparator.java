package uam.aleksy.deansoffice.applicant;

import uam.aleksy.deansoffice.applicant.data.*;

import java.util.Comparator;

public class ApplicantComparator implements Comparator<Applicant> {

    @Override
    public int compare(Applicant o1, Applicant o2) {

        Class<? extends Applicant> o1Clazz = o1.getClass();
        Class<? extends Applicant> o2Clazz = o2.getClass();

        if (o1Clazz.equals(Student.class)) {
            return compareStudentCase((Student) o1, o2);
        }

        if (o2Clazz.equals(Student.class)) {
            // multiply by -1 because we reversed o2 and o1 params
            return compareStudentCase((Student) o2, o1) * (-1);
        }

        return o1.getPriority() - o2.getPriority();
    }

    private int compareStudentCase(Student student, Applicant applicant) {
        Class<? extends Applicant> applicantClazz = applicant.getClass();

        if (applicantClazz.equals(Student.class)) {
            return 0;
        }

        if (applicantClazz.equals(DoctoralStudent.class) || applicantClazz.equals(Acquaintance.class)) {
            return student.isBringsChocolates() ? 1 : -1;
        }

        if (applicantClazz.equals(Adjunct.class)) {
            return student.isBringsChocolates() && student.isBringsFlowers() ? 1 : -1;
        }

        return student.getPriority() - applicant.getPriority();
    }
}
