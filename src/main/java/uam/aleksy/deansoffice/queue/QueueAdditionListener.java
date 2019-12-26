package uam.aleksy.deansoffice.queue;

import uam.aleksy.deansoffice.applicant.data.Applicant;

import java.util.List;

public interface QueueAdditionListener {

    void applicantAdded(Applicant applicant);

    void applicantsAdded(List<Applicant> applicants);
}
