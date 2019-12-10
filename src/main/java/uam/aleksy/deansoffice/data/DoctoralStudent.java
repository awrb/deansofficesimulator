package uam.aleksy.deansoffice.data;


public class DoctoralStudent extends Applicant {

    public final int PRIORITY = 2;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
