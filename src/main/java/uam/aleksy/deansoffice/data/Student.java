package uam.aleksy.deansoffice.data;

public class Student extends Applicant {

    private static final int PRIORITY = 1;

    public Student() {}

    public Student(boolean bringsFlowers, boolean bringsChocolates) {
        this.bringsFlowers = bringsFlowers;
        this.bringsChocolates = bringsChocolates;
    }

    private boolean bringsFlowers;
    private boolean bringsChocolates;
    private int beersToDrink;
    private float markPunishment;

    public void setBringsFlowers(boolean bringsFlowers) {
        this.bringsFlowers = bringsFlowers;
    }


    public void incrementMarkPunishment() {
        markPunishment += 0.5f;
    }

    public void incrementBeersToDrink() {
        beersToDrink += 1;
    }

    public void setBringsChocolates(boolean bringsChocolates) {
        this.bringsChocolates = bringsChocolates;
    }

    public int getBeersToDrink() {
        return beersToDrink;
    }

    public void setBeersToDrink(int beersToDrink) {
        this.beersToDrink = beersToDrink;
    }

    public boolean isBringsFlowers() {
        return bringsFlowers;
    }

    public boolean isBringsChocolates() {
        return bringsChocolates;
    }

    public int getPriority() {
        return PRIORITY;
    }
}
