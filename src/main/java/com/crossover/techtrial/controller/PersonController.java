/**
 * 
 */
package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.PersonView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.service.PersonService;
import com.crossover.techtrial.service.ServiceResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author crossover
 */

@RestController
public class PersonController {
  
  @Autowired
  PersonService personService;
  
  @PostMapping(path = "/api/person")
  public ResponseEntity<PersonDTO> register(@Valid @RequestBody PersonDTO p) {
      if(p.getId()!=null){
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }  
    ServiceResponse<PersonDTO>  response = personService.save(p);
    if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty()){
          return ResponseEntity.badRequest().header("ErrorMessage", response.getResponseMessage()).build();
      }
    return ResponseEntity.created(URI.create("")).body(response.get());
  }
  
  @GetMapping(path = "/api/person")
  public ResponseEntity<List<PersonDTO>> getAllPersons() {
      ServiceResponse<List<PersonDTO>>  response =personService.getAll();
    return ResponseEntity.ok(response.get());
  }
  
  @GetMapping(path = "/api/person/{person-id}")
  public ResponseEntity<PersonDTO> getPersonById(@PathVariable(name="person-id", required=true)Long personId) {
    ServiceResponse<PersonDTO>  response=  personService.findById(personId);
     if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty()){
          return ResponseEntity.badRequest().header("ErrorMessage", response.getResponseMessage()).build();
      }
      return ResponseEntity.ok(response.get());
    
   // return ResponseEntity.notFound().build();
  }
  
    @GetMapping(path = "/api/person/registrationNumber")
  public ResponseEntity<PersonView> getPersonByRegistrationNumber(@RequestParam("registrationNumber") String registrationNumber) {
    ServiceResponse<PersonView>  response=  personService.findByRegistrationNumber(registrationNumber);
     if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty()){
          return ResponseEntity.badRequest().header("ErrorMessage", response.getResponseMessage()).build();
      }
      return ResponseEntity.ok(response.get());
    
   // return ResponseEntity.notFound().build();
  }
  
}
