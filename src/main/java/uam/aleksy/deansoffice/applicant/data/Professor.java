package uam.aleksy.deansoffice.applicant.data;


public class Professor extends Applicant {

    private static final int PRIORITY = 2;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
