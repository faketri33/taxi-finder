package org.faketri.domain.entity.ride.gateway;

import org.faketri.domain.entity.ride.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {
    Page<Ride> findByUserId(@NonNull UUID userId, Pageable pageable);
}
