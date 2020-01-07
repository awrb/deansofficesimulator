package uam.aleksy.deansoffice.applicant;

import uam.aleksy.deansoffice.applicant.data.*;

import java.util.Comparator;

public class ApplicantComparator implements Comparator<Applicant> {

    //TODO return 1 if rhs should be before lhs
    //     return -1 if lhs should be before rhs
    //     return 0 otherwise (meaning the order stays the same)
    @Override
    public int compare(Applicant lhs, Applicant rhs) {

        Class<? extends Applicant> lhsClazz = lhs.getClass();
        Class<? extends Applicant> rhsClazz = rhs.getClass();

        if (lhsClazz.equals(rhsClazz)) {
            return 0;
        }

        if (lhsClazz.equals(Student.class)) {
            return compareStudentCase((Student) lhs, rhs);
        }

        if (rhsClazz.equals(Student.class)) {
            // multiply by -1 because we reversed o2 and o1 params
            return compareStudentCase((Student) rhs, lhs) * (-1);
        }

        return rhs.getPriority() > lhs.getPriority() ? 1 : -1;
    }

    private int compareStudentCase(Student student, Applicant applicant) {
        Class<? extends Applicant> applicantClazz = applicant.getClass();

        if (applicantClazz.equals(Student.class)) {
            return 0;
        }

        if (applicantClazz.equals(DoctoralStudent.class) || applicantClazz.equals(Acquaintance.class)) {
            return student.isWithChocolates() ? -1 : 1;
        }

        if (applicantClazz.equals(Adjunct.class)) {
            return student.isWithChocolates() && student.isWithFlowers() ? -1 : 1;
        }

        return 1;
    }
}
