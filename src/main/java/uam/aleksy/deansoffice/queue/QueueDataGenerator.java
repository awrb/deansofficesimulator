package uam.aleksy.deansoffice.queue;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomEmployeeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Log
public class QueueDataGenerator {

    private int numOfApplicants;
    private int numOfEmployees;

    private RandomApplicantFactory randomApplicantFactory;

    @Autowired
    public QueueDataGenerator(RandomApplicantFactory randomApplicantFactory,
                              @Value("${N}") int numOfApplicants, @Value("${M}") int numOfEmployees) {
        this.numOfApplicants = numOfApplicants;
        this.numOfEmployees = numOfEmployees;
        this.randomApplicantFactory = randomApplicantFactory;

    }


    public List<Employee> generateEmployees() {
        List<Employee> employees = RandomEmployeeFactory.getRandomEmployees(numOfEmployees);
        return employees;
    }

    public List<Applicant> generateApplicants() {

        List<Applicant> applicants = new ArrayList<>();
        IntStream.rangeClosed(1, numOfApplicants).forEach(i ->
                applicants.add(randomApplicantFactory.getRandomApplicant())
        );
        return applicants;
    }
}
