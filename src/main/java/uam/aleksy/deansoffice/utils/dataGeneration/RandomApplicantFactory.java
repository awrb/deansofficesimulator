package uam.aleksy.deansoffice.utils.dataGeneration;

import com.github.javafaker.Faker;
import uam.aleksy.deansoffice.applicant.data.*;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RandomApplicantFactory {

    private static AtomicLong applicantIdGenerator;

    private static Faker faker;

    static {
        faker = new Faker();
        applicantIdGenerator = new AtomicLong(0);
    }

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

        applicant.setName(faker.name().firstName());
        applicant.setId(applicantIdGenerator.getAndIncrement());

        // todo read from properies
        applicant.setTasks(RandomTaskFactory.getRandomTasks(1, 5,1, 6));

        return applicant;

    }
}
