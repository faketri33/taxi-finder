package org.faketri.infrastructure.persistence.repository;

import org.faketri.infrastructure.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<ProfileEntity, UUID> {
    @Transactional
    @Modifying
    @Query("update ProfileEntity d set d.verification = ?1, d.verificationUpdateAt = CURRENT_TIMESTAMP where d.userId = ?2")
    int updateVerification(Boolean verification, UUID id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity d set d.status = ?1, d.statusUpdateAt = CURRENT_TIMESTAMP where d.userId = ?2")
    int updateStatus(Boolean status, UUID userId);
}
