/**
 * 
 */
package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.RideDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.service.RideService;
import com.crossover.techtrial.service.ServiceResponse;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;

/**
 * RideController for Ride related APIs.
 * @author crossover
 *
 */
@RestController
public class RideController {
  
  @Autowired
  RideService rideService;

  @PostMapping(path ="/api/ride")
  public ResponseEntity<RideDTO> createNewRide(@Valid @RequestBody RideDTO rideDTO) {
      if(rideDTO.getId()!=null){
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      ServiceResponse<RideDTO> response=rideService.save(rideDTO);
      if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty()){
          return ResponseEntity.badRequest().header("ErrorMessage", response.getResponseMessage()).build();
      }
    return ResponseEntity.created(URI.create("")).body(response.get());
  }
  
  @GetMapping(path = "/api/ride/{ride-id}")
  public ResponseEntity<RideDTO> getRideById(@PathVariable(name="ride-id",required=true)Long rideId){
   ServiceResponse<RideDTO> response = rideService.findById(rideId);
    
    if(response.getResponseMessage()!=null && !response.getResponseMessage().isEmpty()){
          return ResponseEntity.badRequest().header("ErrorMessage", response.getResponseMessage()).build();
      }
    
      return ResponseEntity.ok(response.get());
   // return ResponseEntity.notFound().build();
  }
  
  /**
   * This API returns the top 5 drivers with their email,name, total minutes, maximum ride duration in minutes.
   * Only rides that starts and ends within the mentioned durations should be counted.
   * Any rides where either start or endtime is outside the search, should not be considered.
   * 
   * DONT CHANGE METHOD SIGNATURE AND RETURN TYPES
     * @param count
     * @param startTime
     * @param endTime
   * @return
   */
  @GetMapping(path = "/api/top-rides")
  public ResponseEntity<List<TopDriverDTO>> getTopDriver(
      @RequestParam(value="max", defaultValue="5") Long count,
      @RequestParam(value="startTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startTime,
      @RequestParam(value="endTime", required=true) @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endTime){
    List<TopDriverDTO> topDrivers = new ArrayList<>();
    /**
     * Your Implementation Here. And Fill up topDrivers Arraylist with Top
     * 
     */
    
    ServiceResponse<List<TopDriverDTO>> response = rideService.getTopDriver(count, startTime, endTime);
    topDrivers= response.get();
    return ResponseEntity.ok(topDrivers);
    
  }
  
}
