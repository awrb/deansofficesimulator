package uam.aleksy.deansoffice.applicant.data;

public class Adjunct extends Applicant {

    private static final int PRIORITY = 4;

    private int extraTasks;


    public int getExtraTasks() {
        return extraTasks;
    }

    public void incrementExtraTasks() {
        extraTasks += 1;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
