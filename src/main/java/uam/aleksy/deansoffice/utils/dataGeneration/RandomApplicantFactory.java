package uam.aleksy.deansoffice.utils.dataGeneration;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.*;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class RandomApplicantFactory {

    private Faker faker;

    private RandomTaskFactory randomTaskFactory;

    @Autowired
    public RandomApplicantFactory(RandomTaskFactory randomTaskFactory) {
        this.randomTaskFactory = randomTaskFactory;
    }

    @PostConstruct
    private void init() {
        faker = new Faker();
    }

    public Applicant getRandomApplicant() {

        Random random = new Random();

        Applicant applicant;

        int randomInt = random.nextInt(100) + 1;

        if (randomInt <= 65) {
            applicant = new Student();
            ((Student) applicant).setWithChocolates(random.nextBoolean());
            ((Student) applicant).setWithFlowers(random.nextBoolean());
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

        // todo read from properies
        applicant.setTasks(randomTaskFactory.getRandomTasks(1, 5));

        return applicant;

    }
}
