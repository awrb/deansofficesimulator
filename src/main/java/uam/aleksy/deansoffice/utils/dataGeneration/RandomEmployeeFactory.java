package uam.aleksy.deansoffice.utils.dataGeneration;

import com.github.javafaker.Faker;
import uam.aleksy.deansoffice.data.Activity;
import uam.aleksy.deansoffice.data.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomEmployeeFactory {

    private static Faker faker;

    private static Activity[] activities;
    private static int activitiesSize;

    static {
        faker = new Faker();
        activities = Activity.values();
        activitiesSize = activities.length;
    }

    public static Employee getRandomEmployee() {
        return new Employee(false, getRandomActivityCycle(), 10, 10, faker.name().firstName());
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

        // TODO make sure there's at least one work
        return randomOrderActivities;


    }
}
