package uam.aleksy.deansoffice.coordination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.applicant.data.Task;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomEmployeeFactory;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
public class WorkCoordinationServiceTest {

    @Autowired
    private WorkCoordinationService workCoordinationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeQueue officeQueue;

    @Test
    public void testStartHelping() {

        // given
        Employee employee = RandomEmployeeFactory.getRandomEmployee();
        Applicant applicant = new Student();

        // when
        workCoordinationService.startHelping(employee, applicant);

        // then
        Assertions.assertEquals(employeeRepository.getEmployeesApplicant(employee), applicant);

    }

    @Test
    public void testWorkOnNewTask() {
        // given
        Employee employee = RandomEmployeeFactory.getRandomEmployee();
        int energyBeforeWorking = employee.getEnergyLeft();

        int taskEnergyRequirement = 1;
        Applicant applicant = new Student();
        applicant.setTasks(new ArrayList<>(Collections.singletonList(new Task(taskEnergyRequirement))));
        Task task = applicant.getNextTask().get();

        // when
        workCoordinationService.workOnNewTask(employee, applicant, task);

        // then

        // task removed from applicant
        Assertions.assertEquals(applicant.getTasks().size(), 0);

        // employee is now working and got his energy decremented
        Assertions.assertTrue(employee.isBusy());
        Assertions.assertEquals(employee.getEnergyLeft(), energyBeforeWorking - taskEnergyRequirement);
    }

    @Test
    public void testFinishHelping() {

        // given
        Employee employee = RandomEmployeeFactory.getRandomEmployee();
        Applicant applicant = new Student();
        employeeRepository.assignEmployeeToApplicant(employee, applicant);

        // when
        workCoordinationService.finishHelping(employee, applicant);

        // then
        Assertions.assertNull(employeeRepository.getEmployeesApplicant(employee));
    }

}
