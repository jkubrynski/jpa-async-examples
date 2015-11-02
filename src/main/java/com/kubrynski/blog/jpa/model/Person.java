package com.kubrynski.blog.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;

/**
 * @author Jakub Kubrynski
 */
@Entity
@DynamicUpdate
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private String uuid = UUID.randomUUID().toString();

    @OptimisticLock(excluded = true)
    private String firstName;

    @OptimisticLock(excluded = true)
    private String lastName;

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
