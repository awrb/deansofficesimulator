package uam.aleksy.deansoffice.queue;

import org.springframework.stereotype.Component;
import uam.aleksy.deansoffice.applicant.data.Applicant;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class OfficeQueue {

    private Queue<Applicant> queue;

    @PostConstruct
    private void init() {
        queue = new LinkedList<>();
    }

    public void addAll(List<Applicant> applicants) {
        queue.addAll(applicants);
    }

    public Queue<Applicant> getQueue() {
        return queue;
    }

    public void remove() {
        queue.remove();
    }

    public void add(Applicant applicant) {
        queue.add(applicant);
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

