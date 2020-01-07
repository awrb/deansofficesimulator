package uam.aleksy.deansoffice.tour.consequences.data;

/**
 * Utility class to return a {@link Consequence} along with a flag
 * informing whether the consequence was just created or it already existed in
 * {@link uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository}
 */
public class ConsequenceCreatedPair {

    private Consequence consequence;

    public Consequence getConsequence() {
        return consequence;
    }

    public boolean isCreated() {
        return created;
    }

    private boolean created;

    public ConsequenceCreatedPair(Consequence consequence, boolean created) {
        this.consequence = consequence;
        this.created = created;
    }
}
