package com.kubrynski.blog.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kubrynski.blog.jpa.model.Person;
import com.kubrynski.blog.jpa.repo.PersonRepository;

/**
 * @author Jakub Kubrynski
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FirstNameUpdater {

    private final PersonRepository personRepository;

    @Autowired
    public FirstNameUpdater(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void update(String uuid, String newName) {
        Person person = personRepository.findByUuid(uuid);
        person.setFirstName(newName);
    }
}
