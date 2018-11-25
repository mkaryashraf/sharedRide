/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.mapper;

import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.PersonView;
import com.crossover.techtrial.model.Person;
import org.springframework.stereotype.Component;

/**
 *
 * @author Makary Ashraf
 */
@Component
public class PersonMapper {
    public PersonDTO persontoPersonDTO(Person person){
      PersonDTO personDTO = new PersonDTO();
      if(person==null){
          return null;
      }
      personDTO.setId(person.getId());
      personDTO.setEmail(person.getEmail());
      personDTO.setName(person.getName());
      personDTO.setRegistrationNumber(person.getRegistrationNumber());
      return  personDTO;
  }
  
  public Person personDTOtoPerson(PersonDTO personDTO){
      if(personDTO==null){
          return null;
      }
      Person person = new Person();
      person.setEmail(personDTO.getEmail());
      person.setName(personDTO.getName());
      person.setRegistrationNumber(personDTO.getRegistrationNumber());
      return person;
  }
  
  public PersonView persontoPersonView(Person person){
      PersonView personView = new PersonView();
      if(person==null){
          return null;
      }
      personView.setEmail(person.getEmail());
      personView.setName(person.getName());
      personView.setRegistrationNumber(person.getRegistrationNumber());
      return  personView;
  }
}
