package uam.aleksy.deansoffice.tour.consequences.data;

import uam.aleksy.deansoffice.applicant.data.Adjunct;

public class AdjunctConsequences extends Consequence {

    private int extraTasks;

    public AdjunctConsequences() {
        super();
    }

    public AdjunctConsequences(Adjunct adjunct) {
        super(adjunct);
    }

    public AdjunctConsequences(int extraTasks) {
        this.extraTasks = extraTasks;
    }

    public void incrementExtraTasks() {
        extraTasks += 1;
    }

    public int getExtraTasks() {
        return extraTasks;
    }

    public void setExtraTasks(int extraTasks) {
        this.extraTasks = extraTasks;
    }
}
