package uam.aleksy.deansoffice.tour.consequences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.applicant.data.*;
import uam.aleksy.deansoffice.coordination.WorkCoordinationService;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.consequences.data.*;

@SpringBootTest
public class ConsequencesFactoryTest {

    @Autowired
    private ConsequencesFactory consequencesFactory;

    @Autowired
    private WorkCoordinationService workCoordinationService;

    @Test
    public void testConsequenceCreationAcquaintance() {

        // given
        Acquaintance acquaintance = new Acquaintance();

        // when
        AcquaintanceConsequences consequences = (AcquaintanceConsequences) consequencesFactory
                .createConsequence(acquaintance).getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), acquaintance);
        Assertions.assertEquals(consequences.getMinutesComplained(), 15);
    }

    @Test
    public void testConsequenceCreationStudent() {

        // given
        Student student = new Student();
        student.setRoundsWaited(2);

        // when
        StudentConsequences consequences = (StudentConsequences) consequencesFactory
                .createConsequence(student)
                .getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), student);
        Assertions.assertEquals(consequences.getBeersToDrink(), 1);
    }


    @Test
    public void testConsequenceCreationProfessor() {

        // given
        Professor professor = new Professor();

        // when
        ProfessorConsequences consequences = (ProfessorConsequences) consequencesFactory
                .createConsequence(professor)
                .getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), professor);
        Assertions.assertEquals(consequences.getDifferentialDegree(), 1);
    }

    @Test
    public void testConsequenceCreationAdjunct() {

        // given
        Adjunct adjunct = new Adjunct();

        // when
        AdjunctConsequences consequences = (AdjunctConsequences) consequencesFactory
                .createConsequence(adjunct)
                .getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), adjunct);
        Assertions.assertEquals(consequences.getExtraTasks(), 1);
    }

    @Test
    public void testConsequenceCreationDean() {

        // given
        Dean dean = new Dean();
        workCoordinationService.startHelping(new Employee(), dean);
        dean.setRoundsWaited(2);

        // when
        DeanConsequences consequences = (DeanConsequences) consequencesFactory
                .createConsequence(dean)
                .getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), dean);
        Assertions.assertEquals(consequences.getFiredEmployees().size(), 1);
    }

    @Test
    public void testConsequenceCreationDoctoralStudent() {

        // given
        DoctoralStudent doctoralStudent = new DoctoralStudent();

        // when
        DoctoralStudentConsequences consequences = (DoctoralStudentConsequences) consequencesFactory
                .createConsequence(doctoralStudent)
                .getConsequence();

        // then
        Assertions.assertEquals(consequences.getApplicant(), doctoralStudent);
        Assertions.assertEquals(consequences.getPunishedStudents().size(), 1);
    }
}
