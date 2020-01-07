package uam.aleksy.deansoffice.applicant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uam.aleksy.deansoffice.applicant.data.*;

public class ApplicantComparatorTest {

    @Test
    public void testComparatorStudentOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        Student student = new Student();

        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student1 = new Student();
        DoctoralStudent doctoralStudent = new DoctoralStudent();

        // when
        int studentProfessorOrdering = comparator.compare(student, professor);
        int studentAcquaintanceOrdering = comparator.compare(student, acquaintance);
        int studentDeanOrdering = comparator.compare(student, dean);
        int studentAdjunctOrdering = comparator.compare(student, adjunct);
        int studentStudentOrdering = comparator.compare(student, student1);
        int studentDoctoralOrdering = comparator.compare(student, doctoralStudent);

        // then
        Assertions.assertEquals(studentProfessorOrdering, 1);
        Assertions.assertEquals(studentAcquaintanceOrdering, 1);
        Assertions.assertEquals(studentAdjunctOrdering, 1);
        Assertions.assertEquals(studentStudentOrdering, 0);
        Assertions.assertEquals(studentDoctoralOrdering, 1);
        Assertions.assertEquals(studentDeanOrdering, 1);
    }

    @Test
    public void testStudentWithChocolatesOrdering() {
        // given
        ApplicantComparator applicantComparator = new ApplicantComparator();
        Student student = new Student();

        student.setWithChocolates(true);

        DoctoralStudent doctoralStudent = new DoctoralStudent();
        Acquaintance acquaintance = new Acquaintance();

        // when
        int studentDoctoralOrdering = applicantComparator.compare(student, doctoralStudent);
        int studentAcquaintanceOrdering = applicantComparator.compare(student, acquaintance);

        // then
        Assertions.assertEquals(studentAcquaintanceOrdering, -1);
        Assertions.assertEquals(studentDoctoralOrdering, -1);
    }

    @Test
    public void testStudentWithChocolateAndFlowersOrdering() {
        // given
        ApplicantComparator applicantComparator = new ApplicantComparator();
        Student student = new Student();

        student.setWithChocolates(true);
        student.setWithFlowers(true);

        Adjunct adjunct = new Adjunct();

        // when
        int studentAdjunctOrdering = applicantComparator.compare(student, adjunct);
        // then
        Assertions.assertEquals(studentAdjunctOrdering, -1);
    }

    @Test
    public void testComparatorDoctoralStudentOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        DoctoralStudent doctoralStudent = new DoctoralStudent();

        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student = new Student();
        DoctoralStudent doctoralStudent1 = new DoctoralStudent();

        // when
        int doctoralStudentProfessorOrdering = comparator.compare(doctoralStudent, professor);
        int doctoralStudentAcquaintanceOrdering = comparator.compare(doctoralStudent, acquaintance);
        int doctoralStudentDeanOrdering = comparator.compare(doctoralStudent, dean);
        int doctoralStudentAdjunctOrdering = comparator.compare(doctoralStudent, adjunct);
        int doctoralStudentStudentOrdering = comparator.compare(doctoralStudent, student);
        int doctoralStudentOrdering = comparator.compare(doctoralStudent, doctoralStudent1);

        // then
        Assertions.assertEquals(doctoralStudentAcquaintanceOrdering, 1);
        Assertions.assertEquals(doctoralStudentProfessorOrdering, 1);
        Assertions.assertEquals(doctoralStudentDeanOrdering, 1);
        Assertions.assertEquals(doctoralStudentAdjunctOrdering, 1);
        Assertions.assertEquals(doctoralStudentStudentOrdering, -1);
        Assertions.assertEquals(doctoralStudentOrdering, 0);
    }

    @Test
    public void testComparatorAdjunctOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        DoctoralStudent doctoralStudent = new DoctoralStudent();
        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student = new Student();
        Adjunct adjunct1 = new Adjunct();

        // when
        int adjunctProfessorOrdering = comparator.compare(adjunct, professor);
        int adjunctAcquaintanceOrdering = comparator.compare(adjunct, acquaintance);
        int adjunctDeanOrdering = comparator.compare(adjunct, dean);
        int adjunctDoctoralStudentOrdering = comparator.compare(adjunct, doctoralStudent);
        int adjunctStudentOrdering = comparator.compare(adjunct, student);
        int adjunctOrdering = comparator.compare(adjunct, adjunct1);

        // then
        Assertions.assertEquals(adjunctAcquaintanceOrdering, -1);
        Assertions.assertEquals(adjunctDeanOrdering, 1);
        Assertions.assertEquals(adjunctOrdering, 0);
        Assertions.assertEquals(adjunctDoctoralStudentOrdering, -1);
        Assertions.assertEquals(adjunctProfessorOrdering, 1);
        Assertions.assertEquals(adjunctStudentOrdering, -1);
    }

    @Test
    public void testComparatorAcquaintanceOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        DoctoralStudent doctoralStudent = new DoctoralStudent();
        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student = new Student();
        Acquaintance acquaintance1 = new Acquaintance();

        // when
        int acquaintanceProfessorOrdering = comparator.compare(acquaintance, professor);
        int acquaintanceDeanOrdering = comparator.compare(acquaintance, dean);
        int acquaintanceDoctoralStudentOrdering = comparator.compare(acquaintance, doctoralStudent);
        int acquaintanceStudentOrdering = comparator.compare(acquaintance, student);
        int acquaintanceOrdering = comparator.compare(acquaintance, acquaintance1);
        int acquaintanceAdjunctOrdering = comparator.compare(acquaintance, adjunct);

        // then
        Assertions.assertEquals(acquaintanceOrdering, 0);
        Assertions.assertEquals(acquaintanceDeanOrdering, 1);
        Assertions.assertEquals(acquaintanceDoctoralStudentOrdering, -1);
        Assertions.assertEquals(acquaintanceProfessorOrdering, 1);
        Assertions.assertEquals(acquaintanceStudentOrdering, -1);
        Assertions.assertEquals(acquaintanceAdjunctOrdering, 1);
    }

    @Test
    public void testComparatorProfessorOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        DoctoralStudent doctoralStudent = new DoctoralStudent();
        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student = new Student();
        Professor professor1 = new Professor();

        // when
        int professorProfessorOrdering = comparator.compare(professor, professor1);
        int professorDeanOrdering = comparator.compare(professor, dean);
        int professorDoctoralStudentOrdering = comparator.compare(professor, doctoralStudent);
        int professorStudentOrdering = comparator.compare(professor, student);
        int professorAcquaintanceOrdering = comparator.compare(professor, acquaintance);
        int professorAdjunctOrdering = comparator.compare(professor, adjunct);

        // then
        Assertions.assertEquals(professorAdjunctOrdering, -1);
        Assertions.assertEquals(professorDeanOrdering, 1);
        Assertions.assertEquals(professorDoctoralStudentOrdering, -1);
        Assertions.assertEquals(professorStudentOrdering, -1);
        Assertions.assertEquals(professorProfessorOrdering, 0);
        Assertions.assertEquals(professorAcquaintanceOrdering, -1);
    }

    @Test
    public void testComparatorDeanOrdering() {

        // given
        ApplicantComparator comparator = new ApplicantComparator();
        DoctoralStudent doctoralStudent = new DoctoralStudent();
        Professor professor = new Professor();
        Acquaintance acquaintance = new Acquaintance();
        Dean dean = new Dean();
        Adjunct adjunct = new Adjunct();
        Student student = new Student();
        Dean dean1 = new Dean();

        // when
        int deanProfessorOrdering = comparator.compare(dean, professor);
        int deanDeanOrdering = comparator.compare(dean, dean);
        int deanDoctoralStudentOrdering = comparator.compare(dean, doctoralStudent);
        int deanStudentOrdering = comparator.compare(dean, student);
        int deanAcquaintanceOrdering = comparator.compare(dean, acquaintance);
        int deanAdjunctOrdering = comparator.compare(dean, adjunct);

        // then
        Assertions.assertEquals(deanAcquaintanceOrdering, -1);
        Assertions.assertEquals(deanAdjunctOrdering, -1);
        Assertions.assertEquals(deanDeanOrdering, 0);
        Assertions.assertEquals(deanDoctoralStudentOrdering, -1);
        Assertions.assertEquals(deanStudentOrdering, -1);
        Assertions.assertEquals(deanProfessorOrdering, -1);
    }
}

