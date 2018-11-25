/**
 *
 */
package com.crossover.techtrial.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.crossover.techtrial.CrossRideApplication;
import com.crossover.techtrial.H2TestProfileDatabaseConfig;
import com.crossover.techtrial.JacksonMessageConverterConfig;
import com.crossover.techtrial.dto.PersonDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.repositories.PersonRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author kshah
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {
    CrossRideApplication.class,
    H2TestProfileDatabaseConfig.class,
    JacksonMessageConverterConfig.class})
public class PersonControllerTest {

    MockMvc mockMvc;

    @Autowired
    private PersonController personController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    PersonRepository personRepository;
    
    @Qualifier("jacksonMessageConverter")
    @Autowired
    MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Qualifier( "jacksonObjectMapper")
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
//        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        jacksonMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(personController).setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    private PersonDTO createPersonDTO(String name, String email, String registrationNumber) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(name);
        personDTO.setEmail(email);
        personDTO.setRegistrationNumber(registrationNumber);
        return personDTO;
    }

    @Test
    @Transactional
    public void testPanelShouldBeRegistered() throws Exception {
//    HttpEntity<Object> person = getHttpEntity(
//        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
//            + " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");
//    ResponseEntity<PersonDTO> response = template.postForEntity(
//        "/api/person", person, PersonDTO.class);
// 
        PersonDTO personDTO = createPersonDTO("test 1", "test10000000000001@gmail.com", "41DCT");
        mockMvc.perform(post("/api/person").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value(personDTO.getName()));
        //Delete this user
        //  personRepository.deleteById(response.getBody().getId());
//    Assert.assertEquals("test 1",  response.getBody().getName());
//    Assert.assertEquals(201,response.getStatusCode().value());
    }

    @Test
    @Transactional
    public void getPersonById() throws Exception {
        PersonDTO personDTO = createPersonDTO("test 1", "test10000000000001@gmail.com", "41DCT");
        mockMvc.perform(post("/api/person").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isCreated());

        Long id = personRepository.findAll().iterator().next().getId();
        mockMvc.perform(get("/api/person/{person-id}", id)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(personDTO.getName()))
                .andExpect(jsonPath("$.registrationNumber").value(personDTO.getRegistrationNumber()));
    }

    @Test
    @Transactional
    public void getPersonByRegistrationNumber() throws Exception {
        PersonDTO personDTO = createPersonDTO("test 1", "test10000000000001@gmail.com", "41DCT");
        mockMvc.perform(post("/api/person").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/person/registrationNumber")
                .contentType(APPLICATION_JSON_UTF8)
                .param("registrationNumber", personDTO.getRegistrationNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(personDTO.getName()))
                .andExpect(jsonPath("$.registrationNumber").value(personDTO.getRegistrationNumber()));
    }

    @Test
    public void testCreatePersonsWithTheSameRegNum() throws Exception {

        PersonDTO personDTO = createPersonDTO("test 1", "test10000000000001@gmail.com", "41DCT");
        mockMvc.perform(post("/api/person").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(personDTO)))
                .andExpect(status().isCreated());

        PersonDTO personDTO2 = createPersonDTO("test 2", "test20000000000002@gmail.com", "41DCT");
        mockMvc.perform(post("/api/person").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(personDTO2)))
                .andExpect(status().isBadRequest());

    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }

    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        JavaTimeModule module = new JavaTimeModule();
//        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

}
