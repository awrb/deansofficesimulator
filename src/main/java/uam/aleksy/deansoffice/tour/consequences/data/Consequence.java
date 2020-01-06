package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;

public class Consequence {

    protected Applicant applicant;

    public Consequence() {
    }

    public Consequence(Applicant applicant) {
        this.applicant = applicant;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
