/**
 * 
 */
package com.crossover.techtrial.repositories;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.crossover.techtrial.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Person repository for basic operations on Person entity.
 * @author crossover
 */
@RestResource(exported=false)
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
  Optional<Person> findById(Long id);
  
    @Query("select person from Person person where person.registrationNumber = :registrationNumber")
    Person  findbyRegistrationNumber(@Param("registrationNumber") String registrationNumber);
}
