/**
 * 
 */
package com.crossover.techtrial.repositories;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import org.springframework.data.domain.*;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.domain.Page;


/**
 * @author crossover
 *
 */
@RestResource(exported = false)
public interface RideRepository extends CrudRepository<Ride, Long> {
    @Query("select new com.crossover.techtrial.dto.TopDriverDTO(ride.driver.name, ride.driver.email ,sum(ride.durationMinutes),"
            + " max(ride.durationMinutes),avg(ride.distance)) from Ride ride"
            + " where (ride.startTime>= :startTime and ride.startTime<endTime )"
            + " and (ride.endTime> :startTime and ride.endTime<= :endTime )"
            + " group by ride.driver.name"
            + " order by max(ride.durationMinutes) desc ")
    Page<TopDriverDTO> getTopDriver(Pageable pageRequest,@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);
    
}
