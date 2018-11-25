/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.PersonView;
import java.util.List;

/**
 * PersonService interface for Persons.
 * @author cossover
 *
 */
public interface PersonService {
  public ServiceResponse<List<PersonDTO>> getAll();
  
  public ServiceResponse<PersonDTO> save(PersonDTO p);
  
  public ServiceResponse<PersonDTO> findById(Long personId);
  
  
  public ServiceResponse<PersonView> findByRegistrationNumber(String regestrationNumber);
  
}
