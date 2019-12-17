package uam.aleksy.deansoffice.applicant.data;


public class Professor extends Applicant {

    private static final int PRIORITY = 5;

    private int differentialDegree;

    public int getDifferentialDegree() {
        return differentialDegree;
    }

    public void incrementDifferentialDegree() {
        differentialDegree += 1;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
