package uam.aleksy.deansoffice.utils.dataGeneration;

import uam.aleksy.deansoffice.data.*;

import java.util.Random;

public class RandomApplicantFactory {

    public static Applicant getRandomApplicant() {

        Random random = new Random();

        Applicant applicant;

        int randomInt = random.nextInt(100) + 1;

        if (randomInt <= 65) {
            applicant = new Student();
        } else if (randomInt <= 72) {
            applicant = new DoctoralStudent();
        } else if (randomInt <= 87) {
            applicant = new Acquaintance();
        } else if (randomInt <= 93) {
            applicant = new Adjunct();
        } else if (randomInt <= 98) {
            applicant = new Professor();
        } else {
            applicant = new Dean();
        }

        applicant.setName(""+applicant.hashCode());

        // todo read from spring profile
        applicant.setTasks(RandomTaskFactory.getRandomTasks(1, 6, 8));

        return applicant;

    }
}
