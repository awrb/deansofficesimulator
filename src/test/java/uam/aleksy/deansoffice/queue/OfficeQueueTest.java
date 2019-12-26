package uam.aleksy.deansoffice.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import uam.aleksy.deansoffice.applicant.data.Dean;
import uam.aleksy.deansoffice.applicant.data.Student;

import java.util.Arrays;

public class OfficeQueueTest {

    @Test
    public void testRemove() {

        OfficeQueue queue = getQueue();

        // given
        queue.add(new Student());

        // when
        queue.remove();

        // then
        Assertions.assertTrue(queue.getQueue().peek() == null);
    }

    @Test
    public void testFlush() {
        // given
        OfficeQueue queue = getQueue();
        queue.add(new Student());
        queue.add(new Student());

        // when
        queue.flush();

        // then
        Assertions.assertTrue(queue.getQueue().isEmpty());
    }

    @Test
    public void testAdd() {
        // given
        OfficeQueue queue = getQueue();

        // when
        queue.add(new Student());
        queue.add(new Student());

        // then
        Assertions.assertEquals(queue.getQueue().size(), 2);
    }

    @Test
    public void testAddAll() {
        // given
        OfficeQueue queue = getQueue();

        // when
        queue.addAll(Arrays.asList(new Student(), new Student(), new Dean()));

        // then
        Assertions.assertEquals(queue.getQueue().size(), 3);
    }

    private OfficeQueue getQueue() {
        QueueRemovalPublisher removalPublisher = Mockito.mock(QueueRemovalPublisher.class);
        OfficeQueue queue = new OfficeQueue(removalPublisher);
        ReflectionTestUtils.invokeMethod(queue, "init", null);
        return queue;
    }
}

