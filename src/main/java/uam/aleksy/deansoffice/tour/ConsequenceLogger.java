package uam.aleksy.deansoffice.tour;

import lombok.extern.java.Log;
import uam.aleksy.deansoffice.tour.data.Consequence;

@Log
class ConsequenceLogger {


    static void reportConsequence(Consequence consequence) {
        log.info(consequence.getApplicantName() + " "
                + consequence.getConsequenceType().getValue() + " "
                + consequence.getConsequenceValue()
        );
    }
}
