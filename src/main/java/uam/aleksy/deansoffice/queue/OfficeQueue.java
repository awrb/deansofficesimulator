package uam.aleksy.deansoffice.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.ApplicantComparator;
import uam.aleksy.deansoffice.applicant.data.Applicant;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
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
        queue = new PriorityQueue<>(new ApplicantComparator());
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

    public Applicant[] getOrderedQueue() {
        PriorityQueue<Applicant> priorityQueueCopy = new PriorityQueue<>(queue);
        Applicant[] queueAsArray = priorityQueueCopy.toArray(new Applicant[queue.size()]);
        Arrays.sort(queueAsArray, new ApplicantComparator());

        return queueAsArray;
    }
}

