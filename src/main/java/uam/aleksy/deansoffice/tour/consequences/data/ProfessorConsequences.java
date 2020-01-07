package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Professor;

public class ProfessorConsequences extends Consequence {

    private int differentialDegree;

    public ProfessorConsequences(Professor professor) {
        super(professor);
    }

    public ProfessorConsequences() {
        super();
    }

    public int getDifferentialDegree() {
        return differentialDegree;
    }

    public void setDifferentialDegree(int differentialDegree) {
        this.differentialDegree = differentialDegree;
    }

    public void incrementDifferentialDegree() {
        differentialDegree += 1;
    }

}
