package uam.aleksy.deansoffice.applicant.rest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@Log
@RestController
@RequestMapping("applicant")
public class ApplicantService {

    private OfficeQueue officeQueue;

    @Autowired
    public ApplicantService(OfficeQueue officeQueue) {
        this.officeQueue = officeQueue;
    }

    /**
     * Add applicants to the queue in runtime with POST requests
     *
     * @return created applicant
     */
    @PostMapping
    public Applicant createApplicant(@RequestBody Applicant applicant) {
        officeQueue.add(applicant);
        log.info("Added a new applicant to the queue: " + applicant.getName());
        return applicant;
    }
}
