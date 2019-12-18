package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Student;
import uam.aleksy.deansoffice.employee.data.Employee;

import java.util.Optional;

/**
 * Used for storing context to consequences
 */
public class ConsequenceContext {

    /**
     * Empty optional when there's no student to be punished
     */
    private Optional<Student> punishedStudent;

    private Employee firedEmployee;

    public Optional<Student> getPunishedStudent() {
        return punishedStudent;
    }

    public Employee getFiredEmployee() {
        return firedEmployee;
    }

    public void setFiredEmployee(Employee firedEmployee) {
        this.firedEmployee = firedEmployee;
    }

    public void setPunishedStudent(Optional<Student> punishedStudent) {
        this.punishedStudent = punishedStudent;
    }
}
