package uam.aleksy.deansoffice.applicant.data;


public class Acquaintance extends Applicant {

    private static final int PRIORITY = 3;

    private int minutesComplained;

    public int getMinutesComplained() {
        return minutesComplained;
    }

    public void incrementMinutesComplained() {
        this.minutesComplained += 15;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
