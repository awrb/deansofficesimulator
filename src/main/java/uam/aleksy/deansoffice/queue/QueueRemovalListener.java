package uam.aleksy.deansoffice.queue;

import uam.aleksy.deansoffice.applicant.data.Applicant;

public interface QueueRemovalListener {

    void onRemove(Applicant applicant);
}
