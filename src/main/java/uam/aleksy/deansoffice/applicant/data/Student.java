package uam.aleksy.deansoffice.applicant.data;

public class Student extends Applicant {

    // the lowest priority
    private static final int PRIORITY = 1;
    private boolean bringsFlowers;
    private boolean bringsChocolates;

    public Student() {
    }

    public boolean isBringsFlowers() {
        return bringsFlowers;
    }

    public void setBringsFlowers(boolean bringsFlowers) {
        this.bringsFlowers = bringsFlowers;
    }

    public boolean isBringsChocolates() {
        return bringsChocolates;
    }

    public void setBringsChocolates(boolean bringsChocolates) {
        this.bringsChocolates = bringsChocolates;
    }

    public int getPriority() {
        return PRIORITY;
    }
}
