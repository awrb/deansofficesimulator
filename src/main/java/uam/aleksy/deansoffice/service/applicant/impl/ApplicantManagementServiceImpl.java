package uam.aleksy.deansoffice.service.applicant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Task;
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

    @Override
    public void removeTask(Applicant applicant) {
        List<Task> tasks = applicant.getTasks();
        if (tasks.size() > 0) {
            tasks.remove(0);
        }
    }

    @Override
    public Task getNextTask(@NonNull Applicant applicant) {
        List<Task> tasks = applicant.getTasks();
        if (tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }
}
