package uam.aleksy.deansoffice.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uam.aleksy.deansoffice.data.Applicant;
import uam.aleksy.deansoffice.data.Tour;
import uam.aleksy.deansoffice.repository.api.TourRepository;
import uam.aleksy.deansoffice.utils.dataGeneration.RandomApplicantFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Repository
public class ApplicantRepositoryImpl implements uam.aleksy.deansoffice.repository.api.ApplicantRepository {


    private static final int NUM_OF_APPLICANTS = 100;

    private TourRepository tourRepository;

    @Autowired
    public ApplicantRepositoryImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    private List<Applicant> applicants;


    @PostConstruct
    private void createApplicants() {
        applicants = new ArrayList<>();
        IntStream.rangeClosed(1, NUM_OF_APPLICANTS).forEach(i -> {
            applicants.add(RandomApplicantFactory.getRandomApplicant());
        });
    }

    @Override
    public List<Applicant> getApplicants() {
        return applicants;
    }

    @Override
    public List<Applicant> getApplicantsByTour(Tour tour) {
        return tourRepository.getTourApplicantsMap().get(tour);
    }
}
