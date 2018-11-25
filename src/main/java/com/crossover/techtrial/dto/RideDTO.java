/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossover.techtrial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Makary Ashraf
 */
public class RideDTO {
  
  private Long id;

  @NotNull
  @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
 
  private LocalDateTime startTime;
  
  @NotNull
  @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
 
  private LocalDateTime endTime;
  
  private Long distance;
  
  @NotNull
  private String driverRegestrationNumber;
  
  @NotNull
  private String riderRegestrationNumber;
  
  
   private Long durationMinutes;
   
   private PersonView driver;
   
   private PersonView rider;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the distance
     */
    public Long getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(Long distance) {
        this.distance = distance;
    }

    /**
     * @return the driverRegestrationNumber
     */
    public String getDriverRegestrationNumber() {
        return driverRegestrationNumber;
    }

    /**
     * @param driverRegestrationNumber the driverRegestrationNumber to set
     */
    public void setDriverRegestrationNumber(String driverRegestrationNumber) {
        this.driverRegestrationNumber = driverRegestrationNumber;
    }

    /**
     * @return the riderRegestrationNumber
     */
    public String getRiderRegestrationNumber() {
        return riderRegestrationNumber;
    }

    /**
     * @param riderRegestrationNumber the riderRegestrationNumber to set
     */
    public void setRiderRegestrationNumber(String riderRegestrationNumber) {
        this.riderRegestrationNumber = riderRegestrationNumber;
    }

    /**
     * @return the durationMinutes
     */
    public Long getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * @param durationMinutes the durationMinutes to set
     */
    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * @return the driver
     */
    public PersonView getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(PersonView driver) {
        this.driver = driver;
    }

    /**
     * @return the rider
     */
    public PersonView getRider() {
        return rider;
    }

    /**
     * @param rider the rider to set
     */
    public void setRider(PersonView rider) {
        this.rider = rider;
    }
   
   
}
