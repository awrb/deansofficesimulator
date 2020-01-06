package uam.aleksy.deansoffice.applicant.data;


public class Dean extends Applicant {

    private static final int PRIORITY = 1;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
