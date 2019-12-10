package uam.aleksy.deansoffice.service.applicant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.repository.api.ApplicantRepository;
import uam.aleksy.deansoffice.service.applicant.api.ApplicantManagementService;

import java.util.List;


@Service
public class ApplicantManagementServiceImpl implements ApplicantManagementService {

    @Autowired
    private ApplicantRepository repository;

    public List<Applicant> getApplicants() {
        return repository.getApplicants();
    }

}
