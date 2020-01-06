package uam.aleksy.deansoffice.applicant.data;

public class Adjunct extends Applicant {

    private static final int PRIORITY = 3;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
