package uam.aleksy.deansoffice.applicant.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "Student"),
        @JsonSubTypes.Type(value = Dean.class, name = "Dean"),
        @JsonSubTypes.Type(value = Acquaintance.class, name = "Acquaintance"),
        @JsonSubTypes.Type(value = DoctoralStudent.class, name = "DoctoralStudent"),
        @JsonSubTypes.Type(value = Adjunct.class, name = "Adjunct"),
        @JsonSubTypes.Type(value = Professor.class, name = "Professor")}
)
public abstract class Applicant {

    private List<Task> tasks; // TODO walidacja max 3

    private String name;

    private int roundsWaited;

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

    @JsonIgnore
    public abstract int getPriority();

    @JsonIgnore
    public Optional<Task> getNextTask() {
        return tasks.stream().findFirst();
    }

    public void removeTask() {
        tasks.stream().findFirst().ifPresent(tasks::remove);
    }
}
