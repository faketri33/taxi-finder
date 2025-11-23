package org.faketri.domain.entity.driver.gateway;

import org.faketri.domain.entity.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    @Transactional
    @Modifying
    @Query("update Driver d set d.verification = ?1, d.verificationUpdateAt = CURRENT_TIMESTAMP where d.userId = ?2")
    int updateVerification(Boolean verification, UUID id);

    @Transactional
    @Modifying
    @Query("update Driver d set d.status = ?1, d.statusUpdateAt = CURRENT_TIMESTAMP where d.userId = ?2")
    int updateStatus(Boolean status, UUID userId);
}
