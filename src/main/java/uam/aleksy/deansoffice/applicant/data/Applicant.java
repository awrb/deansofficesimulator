package uam.aleksy.deansoffice.applicant.data;


import java.util.List;
import java.util.Optional;

public abstract class Applicant {

    private List<Task> tasks; // TODO walidacja max 3

    private String name;

    private int roundsWaited;


    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public abstract int getPriority();

    public Optional<Task> getNextTask() {
        return tasks.stream().findFirst();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        return id.equals(((Applicant) obj).getId());
    }

    public void removeTask() {
        tasks.stream().findFirst().ifPresent(tasks::remove);
    }
}
