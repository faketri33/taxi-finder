package org.faketri.domain.entity.ride.model;

import dto.CarType;
import dto.rideStatus.RideStatus;
import jakarta.persistence.*;
import org.faketri.domain.entity.address.model.Address;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID userId;
    private UUID driverId;
    private UUID paymentsId;
    private RideStatus status;
    private CarType carType;
    @Embedded
    @AttributeOverride(name = "latitude", column = @Column(name = "start_lat"))
    @AttributeOverride(name = "longitude", column = @Column(name = "start_lon"))
    private Address startAddress;

    @Embedded
    @AttributeOverride(name = "latitude", column = @Column(name = "end_lat"))
    @AttributeOverride(name = "longitude", column = @Column(name = "end_lon"))
    private Address endAddress;

    private LocalDateTime createAt;
    private LocalDateTime cancelAt;

    public Ride(UUID id, UUID userId, UUID driverId, UUID paymentsId, RideStatus status, CarType carType, Address startAddress, Address endAddress) {
        this.id = id;
        this.userId = userId;
        this.driverId = driverId;
        this.paymentsId = paymentsId;
        this.status = status;
        this.carType = carType;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public Ride() {
    }

    @PrePersist
    private void setCreateAt(){
        createAt = LocalDateTime.now();
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

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public UUID getPaymentsId() {
        return paymentsId;
    }

    public void setPaymentsId(UUID paymentsId) {
        this.paymentsId = paymentsId;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Address startAddress) {
        this.startAddress = startAddress;
    }

    public Address getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(Address endAddress) {
        this.endAddress = endAddress;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getCancelAt() {
        return cancelAt;
    }

    public void setCancelAt(LocalDateTime cancelAt) {
        this.cancelAt = cancelAt;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ride ride = (Ride) o;
        return getId() != null && Objects.equals(getId(), ride.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
