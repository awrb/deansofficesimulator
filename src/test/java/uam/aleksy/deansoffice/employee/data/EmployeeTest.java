package uam.aleksy.deansoffice.employee.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.enums.Activity;

import java.util.Arrays;
import java.util.List;

public class EmployeeTest {

    @Test
    public void testFinishWork() {

        // given
        Employee employee = new Employee();
        employee.setEnergyLeft(10);

        // when
        employee.finishWork();

        // then
        Assertions.assertEquals(employee.getEnergyLeft(), 0);
    }

    @Test
    public void testWorkOnTask() {
        // given
        Employee employee = new Employee();
        employee.setEnergyLeft(6);
        Task task = new Task(5);

        // when
        employee.workOnTask(task);

        // then
        Assertions.assertEquals(employee.getEnergyLeft(), 1);
    }


    @Test
    public void testResetEnergy() {
        // given
        int initialEnergy = 5;
        int energyLeft = 0;
        Employee employee = new Employee(false, null, energyLeft, initialEnergy, "test", 0L);

        // when
        employee.resetEnergy();

        // then
        Assertions.assertEquals(employee.getEnergyLeft(), initialEnergy);
    }

    @Test
    public void testIsWorking() {
        // given
        Employee employee = new Employee();
        List<Activity> activities = Arrays.asList(Activity.FOOD, Activity.PHONE, Activity.WORK_ON_TASK);
        employee.setActivityCycle(activities);

        // when
        employee.setCurrentActivityIndex(2);

        // then
        Assertions.assertTrue(employee.isWorking());
    }

    @Test
    public void testIsNotWorking() {
        // given
        Employee employee = new Employee();
        List<Activity> activities = Arrays.asList(Activity.FOOD, Activity.PHONE, Activity.WORK_ON_TASK);
        employee.setActivityCycle(activities);

        // when
        employee.setCurrentActivityIndex(0);

        // then
        Assertions.assertFalse(employee.isWorking());
    }

    @Test
    public void testCanWorkOnTask() {
        // given
        Employee employee = new Employee();
        Task task = new Task(5);

        // when
        employee.setEnergyLeft(5);


        Assertions.assertTrue(employee.canWorkOnTask(task));
    }

    @Test
    public void testCanNotWorkOnTask() {
        // given
        Employee employee = new Employee();
        Task task = new Task(5);

        // when
        employee.setEnergyLeft(4);


        Assertions.assertFalse(employee.canWorkOnTask(task));
    }
}
