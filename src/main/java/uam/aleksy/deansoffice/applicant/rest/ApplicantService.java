package uam.aleksy.deansoffice.applicant.rest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@RestController
@Log
@RequestMapping("applicant")
public class ApplicantService {

    private OfficeQueue officeQueue;

    private ApplicantRepository repository;

    @Autowired
    public ApplicantService(OfficeQueue officeQueue, ApplicantRepository repository) {
        this.officeQueue = officeQueue;
        this.repository = repository;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long id) {
        return ResponseEntity.of(repository.getById(id));
    }
}
