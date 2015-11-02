package com.kubrynski.blog.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kubrynski.blog.jpa.model.FieldUpdatedEvent;
import com.kubrynski.blog.jpa.model.Person;
import com.kubrynski.blog.jpa.repo.PersonRepository;

/**
 * @author Jakub Kubrynski
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LastNameUpdater {

    private final JdbcTemplate jdbcTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public LastNameUpdater(JdbcTemplate jdbcTemplate,
            ApplicationEventPublisher applicationEventPublisher) {
        this.jdbcTemplate = jdbcTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    public void update(String uuid, String newName) {
        jdbcTemplate.update("UPDATE person SET last_name = ? WHERE uuid = ?", newName, uuid);
        applicationEventPublisher.publishEvent(new FieldUpdatedEvent());
    }

}
