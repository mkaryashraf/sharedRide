/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.crossover.techtrial.CrossRideApplication;
import com.crossover.techtrial.H2TestProfileDatabaseConfig;
import com.crossover.techtrial.JacksonMessageConverterConfig;
import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.jar.Attributes;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author Makary Ashraf
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
    CrossRideApplication.class,
    H2TestProfileDatabaseConfig.class,
    JacksonMessageConverterConfig.class})
public class RideControllerTest {

    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Qualifier("jacksonMessageConverter")
    @Autowired
    MappingJackson2HttpMessageConverter jacksonMessageConverter;
    
    @Qualifier( "jacksonObjectMapper")
    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    RideController rideController;

    private Person person1;

    private Person person2;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private Person person3;

    @Before
    public void setup() {
//        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
//        JavaTimeModule module = new JavaTimeModule();
//        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
//        module.addSerializer(localDateTimeSerializer);
//        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
//                .modules(module)
//                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .build();
//        jacksonMessageConverter.setObjectMapper(mapper);

        mockMvc = MockMvcBuilders.standaloneSetup(rideController).setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();

        person1 = new Person();
        person1.setName("test 1");
        person1.setEmail("test1@gmail.com");
        person1.setRegistrationNumber("4rftgy");
        person1 = personRepository.save(person1);

        person2 = new Person();
        person2.setName("test 2");
        person2.setEmail("test2@gmail.com");
        person2.setRegistrationNumber("4234fgc");
        person2 = personRepository.save(person2);

        
        person3 = new Person();
        person3.setName("test 3");
        person3.setEmail("test3@gmail.com");
        person3.setRegistrationNumber("dfgfdg43");
        person3 = personRepository.save(person3);

    }

    private RideDTO createRideDTO(
            LocalDateTime startTime,
            LocalDateTime endTime,
            Long distance,
            String driverRegestrationNumber,
            String riderRegestrationNumber) {
        RideDTO rideDTO = new RideDTO();
        rideDTO.setDistance(distance);
        rideDTO.setDriverRegestrationNumber(driverRegestrationNumber);
        rideDTO.setRiderRegestrationNumber(riderRegestrationNumber);
        rideDTO.setStartTime(startTime);
        rideDTO.setEndTime(endTime);
        return rideDTO;
    }

    @Test
    public void createRideEntity() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2018-10-02T10:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO = createRideDTO(startTime,
                endTime,
                10L, "4234fgc", "4rftgy");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.distance").value("10"))
                .andExpect(jsonPath("$.driverRegestrationNumber").value("4234fgc"));

    }
    
    @Test
    public void createRideWithWrongDates() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("2018-10-02T12:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2018-10-02T10:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO = createRideDTO(startTime,
                endTime,
                10L, "4234fgc", "4rftgy");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO)))
                .andExpect(status().isBadRequest());
    }
    
    
    @Test
    public void createRidesThatOverlap() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2018-10-02T10:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO = createRideDTO(startTime,
                endTime,
                10L, "4234fgc", "4rftgy");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO)))
                .andExpect(status().isCreated());
        
        LocalDateTime startTime2 = LocalDateTime.parse("2018-10-02T10:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime2 = LocalDateTime.parse("2018-10-02T10:50:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO2 = createRideDTO(startTime2,
                endTime2,
                10L, "4234fgc", "dfgfdg43");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO2)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getRideEntityById() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2018-10-02T10:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO = createRideDTO(startTime,
                endTime,
                10L, "4234fgc", "4rftgy");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO)))
                .andExpect(status().isCreated());

            Long id=    rideRepository.findAll().iterator().next().getId();
        
        mockMvc.perform(get("/api/ride/{ride-id}",id).contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverRegestrationNumber").value("4234fgc"))
                .andExpect(jsonPath("$.riderRegestrationNumber").value("4rftgy"));

    }
    
    @Test
    public void testGetTopDriver()throws Exception{
        LocalDateTime startTime1 = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime1 = LocalDateTime.parse("2018-10-02T10:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO1 = createRideDTO(startTime1,
                endTime1,
                10L, "4234fgc", "4rftgy");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO1)))
                .andExpect(status().isCreated());
        
        LocalDateTime startTime2 = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime2 = LocalDateTime.parse("2018-10-02T12:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO2 = createRideDTO(startTime2,
                endTime2,
                50L, "4rftgy", "4234fgc");

        mockMvc.perform(post("/api/ride").contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(rideDTO2)))
                .andExpect(status().isCreated());
        
        
        LocalDateTime startTime3 = LocalDateTime.parse("2018-10-02T10:20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        LocalDateTime endTime3 = LocalDateTime.parse("2018-10-02T18:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        RideDTO rideDTO3 = createRideDTO(startTime3,
                endTime3,
                50L, "dfgfdg43", "4234fgc");

        mockMvc.perform(get("/api/top-rides").contentType(APPLICATION_JSON_UTF8)
                .param("max", "2")
                .param("startTime","2018-10-02T10:00:00")
                .param("endTime", "2018-10-02T14:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(hasItem(person1.getName())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(person2.getName())))
               // .andExpect(jsonPath("$.[*].name").value(hasItem(person3.getName())))
                .andExpect(jsonPath("$.[*].name").value(hasSize(2)));



    }

    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
//        JavaTimeModule module = new JavaTimeModule();
//        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
//        module.addSerializer(localDateTimeSerializer);
//        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
//                .modules(module)
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .build();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        JavaTimeModule module = new JavaTimeModule();
//        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }
}
