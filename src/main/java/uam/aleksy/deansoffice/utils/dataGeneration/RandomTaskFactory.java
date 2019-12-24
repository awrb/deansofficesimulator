package uam.aleksy.deansoffice.utils.dataGeneration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class RandomTaskFactory {

    private int maxTaskDifficulty;

    public RandomTaskFactory(@Value("${X}") int maxTaskDifficulty) {
        this.maxTaskDifficulty = maxTaskDifficulty;
    }

    public List<Task> getRandomTasks(int min, int max) {

        Random r = new Random();
        int numOfTasks = r.nextInt(max) + min;
        List<Task> tasks = new ArrayList<>();

        IntStream.rangeClosed(1, numOfTasks)
                .forEach(i -> tasks.add(randomTask(maxTaskDifficulty)));
        return tasks;
    }

    private Task randomTask(int maxDifficulty) {
        Random r = new Random();
        return new Task(r.nextInt((maxDifficulty) + 1));
    }
}
