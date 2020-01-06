package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;

public class StudentConsequences extends Consequence {

    private int beersToDrink;
    private float markPunishment;

    public StudentConsequences() {
        super();
    }
    public StudentConsequences(Applicant applicant) {
        super(applicant);
    }

    public int getBeersToDrink() {
        return beersToDrink;
    }

    public void setBeersToDrink(int beersToDrink) {
        this.beersToDrink = beersToDrink;
    }

    public float getMarkPunishment() {
        return markPunishment;
    }

    public void setMarkPunishment(float markPunishment) {
        this.markPunishment = markPunishment;
    }

    public void incrementBeersToDrink() {
        beersToDrink += 1;
    }
}
