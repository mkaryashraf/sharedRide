/**
 *
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.PersonDTO;
import com.crossover.techtrial.dto.PersonView;
import com.crossover.techtrial.mapper.PersonMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import javax.transaction.Transactional;

/**
 * @author crossover
 *
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    /* (non-Javadoc)
   * @see com.crossover.techtrial.service.PersonService#getAll()
     */
    @Override
    public ServiceResponse<List<PersonDTO>> getAll() {
        ServiceResponse<List<PersonDTO>> response = ServiceResponse.empty();
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : personList) {
            personDTOList.add(personMapper.persontoPersonDTO(person));
        }
        response= ServiceResponse.of(personDTOList);
        return response;

    }

    @Override
    public ServiceResponse<PersonDTO> save(PersonDTO p) {
        ServiceResponse<PersonDTO> response = ServiceResponse.empty();
        Person dbPerson = personRepository.findbyRegistrationNumber(p.getRegistrationNumber());
        if(dbPerson!=null){
            response.setResponseMessage("registration Number already exist, Please enter a unique registration number");
            return response;
        }
        Person person = personMapper.personDTOtoPerson(p);
        person = personRepository.save(person);
        p = personMapper.persontoPersonDTO(person);
        response= response.of(p);
        return response;
    }

    @Override
    public ServiceResponse<PersonDTO> findById(Long personId) {
        ServiceResponse<PersonDTO> response = ServiceResponse.empty();
        Optional<Person> dbPerson = personRepository.findById(personId);
        if(!dbPerson.isPresent()){
            response.setResponseMessage("person is not found");
            return response;
        }
        PersonDTO personDTO = personMapper.persontoPersonDTO(dbPerson.get());
        response = ServiceResponse.of(personDTO);
        return response;
    }

    @Override
    public ServiceResponse<PersonView> findByRegistrationNumber(String registrationNumber) {
         ServiceResponse<PersonView> response = ServiceResponse.empty();
        Person persondb = personRepository.findbyRegistrationNumber(registrationNumber);
        if(persondb == null){
            response.setResponseMessage("person is not found");
            return response;
        }
        PersonView personView;
        personView = personMapper.persontoPersonView(persondb);
        response= ServiceResponse.of(personView);
        return response;
    }

}
