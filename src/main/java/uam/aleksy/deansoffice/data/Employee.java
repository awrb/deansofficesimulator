package uam.aleksy.deansoffice.data;


import org.springframework.stereotype.Component;


import java.util.List;

/**
 * Pracownik dziekanatu
 */

@Component
public class Employee {

    private boolean busy;
    private List<Activity> activityCycle; // task, soup, task, task.. //ORDERED
    private int energyLeft;
    String name;

    public void setActivityCycle(List<Activity> activityCycle) {
        this.activityCycle = activityCycle;
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

    public Employee() {
    }

    public Employee(List<Activity> activityCycle) {
        this.activityCycle = activityCycle;
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
}
