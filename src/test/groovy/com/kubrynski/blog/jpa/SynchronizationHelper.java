package com.kubrynski.blog.jpa;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.kubrynski.blog.jpa.model.FieldUpdatedEvent;

/**
 * @author Jakub Kubrynski
 */
@Component
public class SynchronizationHelper {

    private final CountDownLatch latch = new CountDownLatch(2);

    @TransactionalEventListener
    void finished(FieldUpdatedEvent fieldUpdatedEvent) {
        latch.countDown();
    }

    void waitUntilFinished() {
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
