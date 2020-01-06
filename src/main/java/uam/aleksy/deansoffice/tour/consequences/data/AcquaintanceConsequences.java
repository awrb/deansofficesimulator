package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Applicant;

public class AcquaintanceConsequences extends Consequence {

    private int minutesComplained;

    public AcquaintanceConsequences() {
        super();
    }

    public AcquaintanceConsequences(Applicant applicant) {
        super(applicant);
    }

    public void incrementMinutesComplained() {
        minutesComplained += 15;
    }

    public int getMinutesComplained() {
        return minutesComplained;
    }

    public void setMinutesComplained(int minutesComplained) {
        this.minutesComplained = minutesComplained;
    }
}
