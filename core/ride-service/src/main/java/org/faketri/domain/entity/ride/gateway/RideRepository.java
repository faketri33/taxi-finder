package org.faketri.domain.entity.ride.gateway;

import dto.rideStatus.RideStatus;
import org.faketri.domain.entity.ride.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
    Page<Ride> findByUserId(@NonNull UUID userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Ride r set r.driverId = ?1, r.status = ?2 where r.id = ?3 and r.status = ?4")
    int rideAccept(UUID driverId, RideStatus status, UUID id, RideStatus status1);
}
