package uam.aleksy.deansoffice.employee;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.tour.TourManager;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomEmployeeFactory;

@SpringBootTest
@Log
public class EmployeeTourManagerTest {

    @Autowired
    private TourManager tourManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testNextTour() {

        // given
        employeeRepository.addEmployees(RandomEmployeeFactory.getRandomEmployees(3));

        // when

        log.info(""+(tourManager==null));
        tourManager.finishRound();

        // then
        for (Employee employee : employeeRepository.getEmployees(employee -> true)) {
            Assertions.assertEquals(employee.getEnergyLeft(), employee.getInitialEnergy());
            // goes from 0 to 1 after calling finishRound once
            Assertions.assertEquals(employee.getCurrentActivityIndex(), 1);
        }
    }
}
