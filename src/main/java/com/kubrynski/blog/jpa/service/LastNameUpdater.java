package com.kubrynski.blog.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.kubrynski.blog.jpa.model.FieldUpdatedEvent;
import com.kubrynski.blog.jpa.model.Person;
import com.kubrynski.blog.jpa.repo.PersonRepository;

/**
 * @author Jakub Kubrynski
 */
@Service
public class LastNameUpdater {

    private final PersonRepository personRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final TransactionTemplate transactionTemplate;

    @Autowired
    public LastNameUpdater(
            PersonRepository personRepository,
            ApplicationEventPublisher applicationEventPublisher,
            PlatformTransactionManager transactionManager) {
        this.personRepository = personRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Async
    public void update(final String uuid, final String newName) {
        while (true) {
            try {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        Person person = personRepository.findByUuid(uuid);
                        person.setLastName(newName);
                        applicationEventPublisher.publishEvent(new FieldUpdatedEvent());
                    }
                });
                return;
            } catch (OptimisticLockingFailureException ignore) {
            }
        }
    }

}
