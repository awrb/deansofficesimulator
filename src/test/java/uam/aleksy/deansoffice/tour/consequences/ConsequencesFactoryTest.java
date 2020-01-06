package uam.aleksy.deansoffice.tour.consequences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;
import uam.aleksy.deansoffice.tour.consequences.data.ConsequenceContext;
import uam.aleksy.deansoffice.tour.consequences.enums.ConsequenceType;

public class ConsequencesFactoryTest {

    @Test
    public void testConsequenceCreationStudent() {

        Student student = new Student();
        int beersToDrink = 5;
        student.setBeersToDrink(beersToDrink);

        Consequence consequence = ConsequencesFactory.createConsequence(student, new ConsequenceContext());
        Assertions.assertEquals(consequence.getConsequenceValue(), beersToDrink);
        Assertions.assertEquals(consequence.getConsequenceType(), ConsequenceType.BEER);
    }
}
