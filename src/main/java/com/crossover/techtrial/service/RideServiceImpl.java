/**
 *
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.mapper.RideMapper;
import com.crossover.techtrial.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.*;


/**
 * @author crossover
 *
 */
@Service
@Transactional
public class RideServiceImpl implements RideService {

    @Autowired
    RideRepository rideRepository;
 
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    RideMapper rideMapper ;

    @Override
    public ServiceResponse<RideDTO> save(RideDTO rideDTO) {
        ServiceResponse<RideDTO> response = ServiceResponse.empty();
        if (rideDTO.getStartTime().isAfter(rideDTO.getEndTime()) || rideDTO.getStartTime().equals(rideDTO.getEndTime())) {
            response.setResponseMessage("end time of the ride can't be before or the same as start time");
            return response;
        }
        Person driver = personRepository.findbyRegistrationNumber(rideDTO.getDriverRegestrationNumber());
        Person rider = personRepository.findbyRegistrationNumber(rideDTO.getRiderRegestrationNumber());
        if(driver==null){
            response.setResponseMessage("driver not found, please enter the right regestration number!");
            return response;
        }
        if(rider==null){
            response.setResponseMessage("rider not found, please enter the right regestration number!");
            return response;
        }
        if(driver.equals(rider)){
            response.setResponseMessage("the rider and the driver can't be the same person!");
            return response;
        }
        Ride ride = rideMapper.rideDTOToRide(rideDTO);
        ride = rideRepository.save(ride);
        rideDTO = rideMapper.rideToRideDTO(ride);
        response = ServiceResponse.of(rideDTO);
        return response;
    }

    @Override
    public ServiceResponse<RideDTO> findById(Long rideId) {
        ServiceResponse<RideDTO> response = ServiceResponse.empty();
        Optional<Ride> dbRide = rideRepository.findById(rideId);
        if(!dbRide.isPresent()){
            response.setResponseMessage("ride not found");
            return response;
        }
        RideDTO rideDTO=  rideMapper.rideToRideDTO(dbRide.get());
        response =ServiceResponse.of(rideDTO);
        return response;
    }
    
    /**
     *
     * @param count
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public ServiceResponse<List<TopDriverDTO>> getTopDriver(Long count, LocalDateTime startTime, LocalDateTime endTime){
        ServiceResponse<List<TopDriverDTO>> response = ServiceResponse.empty();
        List<TopDriverDTO> result = rideRepository.getTopDriver(new PageRequest(0, count.intValue()),startTime, endTime).getContent();
        response = ServiceResponse.of( result);
        return response;
    }

    

}
