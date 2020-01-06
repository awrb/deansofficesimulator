package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.employee.data.Employee;

import java.util.ArrayList;
import java.util.List;

public class DeanConsequences extends Consequence {

    private List<Employee> firedEmployees;

    public DeanConsequences() {
        super();
        firedEmployees = new ArrayList<>();
    }

    public DeanConsequences(Applicant applicant, List<Employee> firedEmployees) {
        super(applicant);
        this.firedEmployees = firedEmployees;
    }

    public List<Employee> getFiredEmployees() {
        return firedEmployees;
    }

    public void setFiredEmployees(List<Employee> firedEmployees) {
        this.firedEmployees = firedEmployees;
    }

    public void addEmployee(Employee employee) {
        firedEmployees.add(employee);
    }
}
