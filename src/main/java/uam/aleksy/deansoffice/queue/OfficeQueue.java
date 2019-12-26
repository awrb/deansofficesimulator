package uam.aleksy.deansoffice.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class OfficeQueue {

    private Queue<Applicant> queue;

    private QueueRemovalPublisher removalPublisher;

    private QueueAdditionPublisher additionPublisher;

    @Autowired
    public OfficeQueue(QueueRemovalPublisher removalPublisher, QueueAdditionPublisher additionPublisher) {
        this.removalPublisher = removalPublisher;
        this.additionPublisher = additionPublisher;
    }

    @PostConstruct
    private void init() {
        queue = new LinkedList<>();
    }

    public void addAll(List<Applicant> applicants) {
        queue.addAll(applicants);
        additionPublisher.applicantsAdded(applicants);
    }

    public Queue<Applicant> getQueue() {
        return queue;
    }

    public void remove() {
        removalPublisher.notifyListeners(queue.remove());
    }

    public void add(Applicant applicant) {
        queue.add(applicant);
        additionPublisher.applicantAdded(applicant);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Applicant peek() {
        return queue.peek();
    }

    public void flush() {
        while (queue.peek() != null) {
            queue.remove();
        }
    }
}

