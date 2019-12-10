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
