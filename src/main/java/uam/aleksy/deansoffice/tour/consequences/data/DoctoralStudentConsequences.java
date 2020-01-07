package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.DoctoralStudent;
import uam.aleksy.deansoffice.applicant.data.Student;

import java.util.ArrayList;
import java.util.List;

public class DoctoralStudentConsequences extends Consequence {

    private List<Student> punishedStudents;

    public DoctoralStudentConsequences() {
        super();
        punishedStudents = new ArrayList<>();
    }

    public DoctoralStudentConsequences(DoctoralStudent doctoralStudent, List<Student> punishedStudents) {
        super(doctoralStudent);
        this.punishedStudents = punishedStudents;
    }

    public List<Student> getPunishedStudents() {
        return punishedStudents;
    }

    public void setPunishedStudents(List<Student> punishedStudents) {
        this.punishedStudents = punishedStudents;
    }

    public void addStudent(Student student) {
        punishedStudents.add(student);
    }
}
