package uam.aleksy.deansoffice.applicant.data;

public class Student extends Applicant {

    // the lowest priority
    private static final int PRIORITY = 1;
    private boolean withFlowers;
    private boolean withChocolates;

    public Student() {
    }

    public boolean isWithFlowers() {
        return withFlowers;
    }

    public void setWithFlowers(boolean withFlowers) {
        this.withFlowers = withFlowers;
    }

    public boolean isWithChocolates() {
        return withChocolates;
    }

    public void setWithChocolates(boolean withChocolates) {
        this.withChocolates = withChocolates;
    }

    public int getPriority() {
        return PRIORITY;
    }
}
