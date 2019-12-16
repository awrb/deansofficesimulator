package uam.aleksy.deansoffice.data;


import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public abstract class Applicant {

    private List<Task> tasks; // TODO walidacja max 3

    private String name;

    private int roundsWaited;

    private int minutesComplained;

    public int getMinutesComplained() {
        return minutesComplained;
    }

    public void incrementMinutesComplained() {
        minutesComplained += 15; // TODO 15 hardcode
    }

    public int getRoundsWaited() {
        return roundsWaited;
    }

    public void setRoundsWaited(int roundsWaited) {
        this.roundsWaited = roundsWaited;
    }

    public void incrementRoundsWaited() {
        this.roundsWaited += 1;
    }

    public String getName() {
        return name;
    }



    //TODO priority abstrakcyjnego konstruktora


    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public abstract int getPriority();

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Optional<Task> getNextTask() {
        return tasks.stream().findFirst();
    }

    public void removeTask() {
        tasks.stream().findFirst().ifPresent(tasks::remove);
    }
}
