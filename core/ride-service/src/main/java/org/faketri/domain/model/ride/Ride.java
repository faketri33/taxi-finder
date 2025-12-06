package org.faketri.domain.model.ride;

import dto.CarType;
import dto.rideStatus.RideStatus;
import org.faketri.infrastructure.persistence.entity.address.model.AddressEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ride {
    private UUID id;
    private UUID userId;
    private UUID driverId;
    private UUID paymentsId;
    private RideStatus status;
    private CarType carType;
    private AddressEntity startAddress;
    private AddressEntity endAddress;

    private LocalDateTime createAt;
    private LocalDateTime cancelAt;

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

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public AddressEntity getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(AddressEntity startAddress) {
        this.startAddress = startAddress;
    }

    public AddressEntity getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(AddressEntity endAddress) {
        this.endAddress = endAddress;
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
}
