package uam.aleksy.deansoffice.tour.consequences;

import lombok.extern.java.Log;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;

@Log
public class ConsequenceLogger {

    public static void reportConsequence(Consequence consequence) {
        // TODO stringbuilder
        log.info(consequence.getApplicantName() + " "
                + consequence.getConsequenceType().getValue() + " "
                + consequence.getConsequenceValue()
        );
    }
}
