package uam.aleksy.deansoffice.tour.consequences.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uam.aleksy.deansoffice.applicant.ApplicantRepository;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.tour.consequences.ConsequenceRepository;
import uam.aleksy.deansoffice.tour.consequences.data.Consequence;

import java.util.Optional;

@RestController
@RequestMapping("consequences")
public class ConsequencesService {

    private ConsequenceRepository consequenceRepository;

    private ApplicantRepository applicantRepository;

    @Autowired
    public ConsequencesService(ConsequenceRepository consequenceRepository, ApplicantRepository applicantRepository) {
        this.consequenceRepository = consequenceRepository;
        this.applicantRepository = applicantRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Consequence> getConsequencesByApplicantId(@PathVariable Long id) {

        Optional<Applicant> optionalApplicant = applicantRepository.getById(id);

        return optionalApplicant.map(applicant -> ResponseEntity.of(
                Optional.ofNullable(consequenceRepository.getConsequencesForApplicant(applicant)))
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
