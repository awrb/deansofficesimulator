package uam.aleksy.deansoffice.applicant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Applicant not found")
public class ApplicantNotFoundException extends RuntimeException {
}