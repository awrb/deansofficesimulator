package uam.aleksy.deansoffice.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.employee.enums.Activity;
import uam.aleksy.deansoffice.tour.TourManager;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class EmployeeOpinionTrackerTest {

    @Autowired
    private TourManager tourManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testEmployeeOpinionModification() {

        // given
        Employee employee = new Employee();
        employee.setActivityCycle(Arrays.asList(Activity.FOOD, Activity.SMOKE, Activity.WORK_ON_TASK));
        employee.setCurrentActivityIndex(0);
        employeeRepository.addEmployees(Collections.singletonList(employee));

        // when
        tourManager.finishRound();

        // then
        // after a round not working, should decrement initial reputation by one so 100 becomes 99
        Assertions.assertEquals(employee.getReputationScore(), 99);
    }
}
