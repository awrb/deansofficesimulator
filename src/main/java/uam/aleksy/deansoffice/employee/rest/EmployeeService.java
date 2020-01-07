package uam.aleksy.deansoffice.employee.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;

@RestController
@RequestMapping("employee")
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Employee> getApplicant(@PathVariable Long id) {
        return ResponseEntity.of(employeeRepository.getById(id));
    }
}
