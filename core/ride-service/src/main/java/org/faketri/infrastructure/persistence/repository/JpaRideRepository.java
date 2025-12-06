package org.faketri.infrastructure.persistence.repository;

import dto.rideStatus.RideStatus;
import org.faketri.infrastructure.persistence.entity.ride.model.RideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface JpaRideRepository extends JpaRepository<RideEntity, UUID> {
    Page<RideEntity> findByUserId(@NonNull UUID userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update RideEntity r set r.driverId = ?1, r.status = ?2 where r.id = ?3 and r.status = ?4")
    int rideAccept(UUID driverId, RideStatus status, UUID id, RideStatus status1);
}
