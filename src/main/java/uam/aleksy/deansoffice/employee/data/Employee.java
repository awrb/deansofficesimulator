package uam.aleksy.deansoffice.employee.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.enums.Activity;
import uam.aleksy.deansoffice.employee.enums.EmployeeReputation;

import java.util.List;

/**
 * Pracownik dziekanatu
 */

public class Employee {

    private static final int INITIAL_REPUTATION = 100;

    private String name;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private boolean busy;
    private List<Activity> activityCycle;
    private int currentActivityIndex;
    private int energyLeft;
    private int initialEnergy;
    private int reputationScore = INITIAL_REPUTATION;
    private EmployeeReputation reputation = EmployeeReputation.DOBRY_CZLOWIEK;


    public Employee(boolean busy, List<Activity> activityCycle, int energyLeft, int initialEnergy, String name, Long id) {
        this.busy = busy;
        this.activityCycle = activityCycle;
        this.energyLeft = energyLeft;
        this.initialEnergy = initialEnergy;
        this.name = name;
        this.id = id;
    }

    public Employee() {
    }

    @JsonProperty
    public int getReputationScore() {
        return reputationScore;
    }

    @JsonIgnore
    public void setReputationScore(int reputationScore) {
        this.reputationScore = reputationScore;
    }

    @JsonProperty
    public EmployeeReputation getReputation() {
        return reputation;
    }

    @JsonIgnore
    public void setReputation(EmployeeReputation reputation) {
        this.reputation = reputation;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public int getCurrentActivityIndex() {
        return currentActivityIndex;
    }

    public void setCurrentActivityIndex(int currentActivityIndex) {
        this.currentActivityIndex = currentActivityIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergyLeft() {
        return energyLeft;
    }

    public void setEnergyLeft(int energyLeft) {
        this.energyLeft = energyLeft;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public List<Activity> getActivityCycle() {
        return activityCycle;
    }

    public void setActivityCycle(List<Activity> activityCycle) {
        this.activityCycle = activityCycle;
    }

    public void finishWork() {
        energyLeft = 0;
    }

    public void workOnTask(Task task) {
        busy = true;
        energyLeft -= task.getDifficulty();
    }

    public boolean canWorkOnTask(Task task) {
        return task.getDifficulty() <= energyLeft;
    }

    public void resetEnergy() {
        energyLeft = initialEnergy;
    }

    @JsonIgnore
    public boolean isWorking() {
        return activityCycle.get(currentActivityIndex).equals(Activity.WORK_ON_TASK);
    }

    public void toggleNextActivity() {
        if (currentActivityIndex == activityCycle.size() - 1) {
            currentActivityIndex = 0;
            return;
        }

        currentActivityIndex += 1;
    }

    @JsonIgnore
    public Activity getCurrentActivity() {
        return activityCycle.get(currentActivityIndex);
    }

    public void decrementReputationScore() {
        reputationScore -= 1;
    }

}
