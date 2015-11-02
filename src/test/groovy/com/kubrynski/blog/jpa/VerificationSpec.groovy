package com.kubrynski.blog.jpa

import com.kubrynski.blog.jpa.model.Person
import com.kubrynski.blog.jpa.repo.PersonRepository
import com.kubrynski.blog.jpa.service.FirstNameUpdater
import com.kubrynski.blog.jpa.service.LastNameUpdater
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

/**
 * @author Jakub Kubrynski
 */
@SpringApplicationConfiguration(classes = JpaAsyncApplication)
class VerificationSpec extends Specification {

    @Autowired
    PersonRepository personRepository

    @Autowired
    FirstNameUpdater firstNameUpdater

    @Autowired
    LastNameUpdater lastNameUpdater

    @Autowired
    SynchronizationHelper helper

    def 'update two fields in separate transactions'() {
        given:
            Person person = new Person()
            personRepository.save(person)
        when:
            firstNameUpdater.update(person.uuid, "Jerry")
            lastNameUpdater.update(person.uuid, "Newman")
        then:
            helper.waitUntilFinished()
            Person personToCheck = personRepository.findByUuid(person.uuid)
            personToCheck.firstName == "Jerry"
            personToCheck.lastName == "Newman"
    }
}
