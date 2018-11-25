/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.RideDTO;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import java.time.LocalDateTime;
import java.util.List;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface RideService {
  
  public ServiceResponse<RideDTO> save(RideDTO rideDTO);
  
  public ServiceResponse<RideDTO> findById(Long rideId);
  
  public ServiceResponse<List<TopDriverDTO>> getTopDriver(Long count, LocalDateTime startTime, LocalDateTime endTime);

}
