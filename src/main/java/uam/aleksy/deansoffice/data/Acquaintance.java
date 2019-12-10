package uam.aleksy.deansoffice.data;


public class Acquaintance extends Applicant {

    private static final int PRIORITY = 3;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
