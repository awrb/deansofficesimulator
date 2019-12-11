package uam.aleksy.deansoffice.data;


import java.util.List;

/**
 * Pracownik dziekanatu
 */

public class Employee {

    private String name;
    private boolean busy;
    private List<Activity> activityCycle;
    private int currentActivityIndex;
    private int energyLeft;

    public int getCurrentActivityIndex() {
        return currentActivityIndex;
    }

    public void setCurrentActivityIndex(int currentActivityIndex) {
        this.currentActivityIndex = currentActivityIndex;
    }

    public Employee(boolean busy, List<Activity> activityCycle, int energyLeft, String name) {
        this.busy = busy;
        this.activityCycle = activityCycle;
        this.energyLeft = energyLeft;
        this.name = name;
        this.currentActivityIndex = 0;
    }

    public Employee() {
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
}
