package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Acquaintance;

public class AcquaintanceConsequences extends Consequence {

    private int minutesComplained;

    public AcquaintanceConsequences() {
        super();
    }

    public AcquaintanceConsequences(Acquaintance acquaintance) {
        super(acquaintance);
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
