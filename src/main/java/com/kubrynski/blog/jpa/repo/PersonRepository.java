package com.kubrynski.blog.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubrynski.blog.jpa.model.Person;

/**
 * @author Jakub Kubrynski
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUuid(String uuid);
}
