package org.faketri.infrastructure.persistence.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.EAGER)
    private Car car;

    @Enumerated(EnumType.STRING)
    private EVerificationStatus verification;

    private Instant createAt;
    private Instant verificationUpdateAt;

    public ProfileEntity(UUID id, UUID userId, Car car, EVerificationStatus verification, Instant createAt, Instant verificationUpdateAt) {
        this.id = id;
        this.userId = userId;
        this.car = car;
        this.verification = verification;
        this.createAt = createAt;
        this.verificationUpdateAt = verificationUpdateAt;
    }

    public ProfileEntity() {

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProfileEntity profileEntity = (ProfileEntity) o;
        return getId() != null && Objects.equals(getId(), profileEntity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
