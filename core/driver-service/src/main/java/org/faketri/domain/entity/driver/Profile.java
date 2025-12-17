package org.faketri.domain.entity.driver;

import java.time.Instant;
import java.util.UUID;

public class Profile {
    private UUID id;

    private UUID userId;
    private Boolean verification;
    private Boolean status;

    private Instant createAt;
    private Instant statusUpdateAt;
    private Instant verificationUpdateAt;


    public Profile(UUID id, UUID userId, Boolean verification, Boolean status, Instant createAt, Instant statusUpdateAt, Instant verificationUpdateAt) {
        this.id = id;
        this.userId = userId;
        this.verification = verification;
        this.status = status;
        this.createAt = createAt;
        this.statusUpdateAt = statusUpdateAt;
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

    public Boolean getVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getStatusUpdateAt() {
        return statusUpdateAt;
    }

    public void setStatusUpdateAt(Instant statusUpdateAt) {
        this.statusUpdateAt = statusUpdateAt;
    }

    public Instant getVerificationUpdateAt() {
        return verificationUpdateAt;
    }

    public void setVerificationUpdateAt(Instant verificationUpdateAt) {
        this.verificationUpdateAt = verificationUpdateAt;
    }
}
