package uam.aleksy.deansoffice.simulation;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.EmployeeRepository;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.queue.OfficeQueue;
import uam.aleksy.deansoffice.queue.QueueDataGenerator;
import uam.aleksy.deansoffice.queue.QueueJsonReader;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Log
@Order(1)
public class Bootstrapper {

    private QueueDataGenerator queueDataGenerator;

    private RandomApplicantFactory randomApplicantFactory;

    private EmployeeRepository employeeRepository;

    private OfficeQueue queue;

    private DataSource dataSource;

    @Autowired
    public Bootstrapper(QueueDataGenerator queueDataGenerator,
                        RandomApplicantFactory randomApplicantFactory,
                        EmployeeRepository employeeRepository,
                        OfficeQueue queue,
                        @Value("${data.source}") DataSource dataSource) {
        this.queueDataGenerator = queueDataGenerator;
        this.randomApplicantFactory = randomApplicantFactory;
        this.employeeRepository = employeeRepository;
        this.queue = queue;
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() throws SimulationInitializationException {

        if (dataSource.equals(DataSource.RANDOM)) {

            List<Applicant> applicants = queueDataGenerator.generateApplicants();
            queue.addAll(applicants);

            List<Employee> employees = queueDataGenerator.generateEmployees();
            employeeRepository.addEmployees(employees);

            return;
        }

        // DataSource.JSON
        List<Applicant> applicants = QueueJsonReader.readApplicants();
        List<Employee> employees = QueueJsonReader.readEmployees();

        queue.addAll(applicants);

        employeeRepository.addEmployees(employees);

    }
}
