package uam.aleksy.deansoffice.applicant.data;


public class Dean extends Applicant {

    private static final int PRIORITY = Integer.MAX_VALUE;

    @Override
    public int getPriority() {
        return 0;
    }
}
