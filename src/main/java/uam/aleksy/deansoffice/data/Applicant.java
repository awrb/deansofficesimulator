package uam.aleksy.deansoffice.data;


import java.util.List;

public abstract class Applicant {

    private List<Task> tasks; // TODO walidacja max 3

    private String name;

    public String getName() {
        return name;
    }

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
}
