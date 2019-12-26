package uam.aleksy.deansoffice.employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.employee.data.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryTest {

    private EmployeeRepository getRepository() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        ReflectionTestUtils.invokeMethod(employeeRepository, "init", null);
        return employeeRepository;
    }

    @Test
    public void testGetEmployees() {
        // given
        EmployeeRepository employeeRepository = getRepository();
        int amountOfEmployees = 3;
        int amountOfBusyEmployees = 2;
        List<Employee> employees = prepareEmployees(amountOfEmployees, amountOfBusyEmployees);

        // when
        employeeRepository.addEmployees(employees);

        // then
        Assertions.assertEquals(employeeRepository.getEmployees(employee -> true).size(), amountOfEmployees);
        Assertions.assertEquals(employeeRepository.getEmployees(Employee::isBusy).size(), 2);
    }

    public void testEmployeeAssignment() {
        // given
        EmployeeRepository employeeRepository = getRepository();
        Employee employee = new Employee();
        Applicant applicant = new Student();

        // when
        employeeRepository.assignEmployeeToApplicant(employee, applicant);

        // then
        Assertions.assertEquals(employeeRepository.getEmployeesApplicant(employee), applicant);
    }

    public void testEmployeeUnassignment() {
        // given
        EmployeeRepository employeeRepository = getRepository();
        Employee employee = new Employee();
        Applicant applicant = new Student();
        employeeRepository.assignEmployeeToApplicant(employee, applicant);

        // when
        employeeRepository.unassignEmployee(employee);

        // then
        Assertions.assertNull(employeeRepository.getEmployeesApplicant(employee));
    }

    public void testRemoveEmployee() {
        // given
        EmployeeRepository employeeRepository = getRepository();
        Employee employee = new Employee();
        employee.setName("testUniqueName");
        Applicant applicant = new Student();
        employeeRepository.assignEmployeeToApplicant(employee, applicant);

        // when
        employeeRepository.removeEmployee(employee);

        // then
        Assertions.assertNull(employeeRepository.getEmployeesApplicant(employee));
        // should also remove employee to repository
        Assertions.assertTrue(employeeRepository.getEmployees(e -> e.getName().equals("testUniqueName")).isEmpty());
    }

    private List<Employee> prepareEmployees(int numOfEmployees, int numOfBusy) {
        List<Employee> employees = new ArrayList<>();
        while (numOfEmployees > 0) {
            Employee employee = new Employee();
            if (numOfBusy > 0) {
                employee.setBusy(true);
            }
            employees.add(employee);
            numOfBusy -= 1;
            numOfEmployees -= 1;
        }
        return employees;
    }
}

