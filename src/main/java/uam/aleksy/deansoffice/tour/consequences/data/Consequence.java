package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.tour.consequences.enums.ConsequenceType;

public class Consequence {


    private ConsequenceType consequenceType;

    public ConsequenceType getConsequenceType() {
        return consequenceType;
    }

    public void setConsequenceType(ConsequenceType consequenceType) {
        this.consequenceType = consequenceType;
    }

    public Consequence(ConsequenceType consequenceType, String applicantName, Object consequenceValue) {
        this.consequenceType = consequenceType;
        this.applicantName = applicantName;
        this.consequenceValue = consequenceValue;
    }

    private String applicantName;

    private Object consequenceValue;

    public Consequence() {
    }


    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }



    public Object getConsequenceValue() {
        return consequenceValue;
    }

    public void setConsequenceValue(Object consequenceValue) {
        this.consequenceValue = consequenceValue;
    }
}
