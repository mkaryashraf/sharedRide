/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.mapper;

import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Makary Ashraf
 */

@Component
public class RideMapper {
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    PersonMapper personMapper;
    
    public Ride rideDTOToRide(RideDTO rideDTO) {
        if (rideDTO == null) {
            return null;
        }
        Ride ride = new Ride();
        ride.setDistance(rideDTO.getDistance());
        ride.setDriver(personRepository.findbyRegistrationNumber(rideDTO.getDriverRegestrationNumber()));
        ride.setRider(personRepository.findbyRegistrationNumber(rideDTO.getRiderRegestrationNumber()));
        ride.setStartTime(rideDTO.getStartTime());
        ride.setEndTime(rideDTO.getEndTime());
        ride.setDurationMinutes(rideDTO.getStartTime().until( rideDTO.getEndTime(),ChronoUnit.MINUTES));
        return ride;
    }

    public RideDTO rideToRideDTO(Ride ride) {
        if (ride == null) {
            return null;
        }
        RideDTO rideDTO = new RideDTO();
        rideDTO.setId(ride.getId());
        rideDTO.setDistance(ride.getDistance());
        rideDTO.setDriverRegestrationNumber(ride.getDriver().getRegistrationNumber());
        rideDTO.setRiderRegestrationNumber(ride.getRider().getRegistrationNumber());
        rideDTO.setStartTime(ride.getStartTime());
        rideDTO.setEndTime(ride.getEndTime());
        rideDTO.setDurationMinutes(ride.getDurationMinutes());
        rideDTO.setDriver(personMapper.persontoPersonView(ride.getDriver()));
        rideDTO.setRider(personMapper.persontoPersonView(ride.getRider()));
        return rideDTO;
    }
}
