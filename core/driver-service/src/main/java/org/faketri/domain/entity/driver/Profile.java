package org.faketri.domain.entity.driver;

import org.faketri.infrastructure.persistence.entity.EVerificationStatus;

import java.time.Instant;
import java.util.UUID;

public class Profile {
    private UUID id;

    private UUID userId;
    private EVerificationStatus verification;

    private Instant createAt;
    private Instant verificationUpdateAt;


    public Profile(UUID id, UUID userId, EVerificationStatus verification, Instant createAt, Instant verificationUpdateAt) {
        this.id = id;
        this.userId = userId;
        this.verification = verification;
        this.createAt = createAt;
        this.verificationUpdateAt = verificationUpdateAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public EVerificationStatus getVerification() {
        return verification;
    }

    public void setVerification(EVerificationStatus verification) {
        this.verification = verification;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getVerificationUpdateAt() {
        return verificationUpdateAt;
    }

    public void setVerificationUpdateAt(Instant verificationUpdateAt) {
        this.verificationUpdateAt = verificationUpdateAt;
    }
}
