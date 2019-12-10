package uam.aleksy.deansoffice.utils.dataGeneration;

import uam.aleksy.deansoffice.data.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomTaskFactory {

    public static List<Task> getRandomTasks(int min, int max, int maxDifficulty) {

        Random r = new Random();
        int numOfTasks = r.nextInt(max) + min;
        List<Task> tasks = new ArrayList<>();

        IntStream.rangeClosed(1, numOfTasks)
                .forEach((i) -> tasks.add(randomTask(maxDifficulty)));
        return tasks;
    }

    private static Task randomTask(int maxDifficulty) {
        Random r = new Random();
        return new Task(r.nextInt(maxDifficulty) + 1);
    }
}
