package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;

public class ProfessorConsequences extends Consequence {

    public ProfessorConsequences(Applicant applicant) {
        super(applicant);
    }

    public ProfessorConsequences() {
        super();
    }

    private int differentialDegree;

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
