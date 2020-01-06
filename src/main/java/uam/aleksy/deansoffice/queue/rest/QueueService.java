package uam.aleksy.deansoffice.queue.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uam.aleksy.deansoffice.applicant.data.Applicant;
import uam.aleksy.deansoffice.queue.OfficeQueue;

@RestController
@RequestMapping("queue")
public class QueueService {

    private OfficeQueue queue;

    public QueueService(OfficeQueue queue) {
        this.queue = queue;
    }

    @Autowired

    @GetMapping
    public Applicant[] getApplicantsInQueue() {
        return queue.getOrderedQueue();
    }
}