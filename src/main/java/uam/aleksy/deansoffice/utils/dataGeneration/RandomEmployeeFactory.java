package uam.aleksy.deansoffice.utils.dataGeneration;

import com.github.javafaker.Faker;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.employee.enums.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class RandomEmployeeFactory {

    private static Faker faker;

    private static Activity[] activities;
    private static int activitiesSize;
    private static AtomicLong idGenerator;

    static {
        faker = new Faker();
        activities = Activity.values();
        activitiesSize = activities.length;
        idGenerator = new AtomicLong(0);
    }

    public static Employee getRandomEmployee() {
        return new Employee(false, getRandomActivityCycle(), 6, 6,
                faker.name().firstName(), idGenerator.incrementAndGet());
    }

    public static List<Employee> getRandomEmployees(int amount) {

        List<Employee> employees = new ArrayList<>();

        IntStream.rangeClosed(1, amount).forEach(i -> {
            employees.add(getRandomEmployee());
        });

        return employees;
    }

    private static List<Activity> getRandomActivityCycle() {

        Random random = new Random();

        List<Activity> randomOrderActivities = new ArrayList<>();

        randomOrderActivities.add(Activity.WORK_ON_TASK);

        IntStream.rangeClosed(1, activitiesSize).forEach(i -> {
            int index = random.nextInt(activitiesSize);
            randomOrderActivities.add(activities[index]);
        });

        return randomOrderActivities;


    }
}
