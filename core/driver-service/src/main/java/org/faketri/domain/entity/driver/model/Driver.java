package org.faketri.domain.entity.driver.model;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private UUID userId;

    private Boolean verification;
    private Boolean status;

    private Instant createAt;
    private Instant statusUpdateAt;
    private Instant verificationUpdateAt;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Driver driver = (Driver) o;
        return getId() != null && Objects.equals(getId(), driver.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
