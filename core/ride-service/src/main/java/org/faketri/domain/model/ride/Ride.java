package org.faketri.domain.model.ride;

import dto.CarType;
import dto.rideStatus.RideStatus;
import org.faketri.domain.exception.RideAcceptException;
import org.faketri.domain.model.address.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ride {
    private UUID id;
    private UUID userId;
    private UUID driverId;
    private UUID paymentsId;
    private BigDecimal price;
    private RideStatus status;
    private CarType carType;
    private Address startAddress;
    private Address endAddress;

    private LocalDateTime createAt;
    private LocalDateTime cancelAt;

    public Ride(UUID id, UUID userId, UUID driverId, UUID paymentsId, BigDecimal price, RideStatus status, CarType carType, Address startAddress, Address endAddress, LocalDateTime createAt, LocalDateTime cancelAt) {
        this.id = id;
        this.userId = userId;
        this.driverId = driverId;
        this.paymentsId = paymentsId;
        this.price = price;
        this.status = status;
        this.carType = carType;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.createAt = createAt;
        this.cancelAt = cancelAt;
    }

    public void accept() {
        if (!this.status.equals(RideStatus.DISPATCHING))
            throw new RideAcceptException("Ride with id: " + id + " is not dispatching");
        status = RideStatus.ACCEPTED;
    }

    public void cancel() {
        if (this.status.equals(RideStatus.CANCEL) || driverId != null)
            throw new RuntimeException("Ride with id: " + id + " already cancel or your not a driver");
        status = RideStatus.CANCEL;
        cancelAt = LocalDateTime.now();
    }


    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public UUID getPaymentsId() {
        return paymentsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public RideStatus getStatus() {
        return status;
    }

    public CarType getCarType() {
        return carType;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Address startAddress) {
        this.startAddress = startAddress;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Address getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(Address endAddress) {
        this.endAddress = endAddress;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getCancelAt() {
        return cancelAt;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setCancelAt(LocalDateTime cancelAt) {
        this.cancelAt = cancelAt;
    }

    public void setPaymentsId(UUID paymentsId) {
        this.paymentsId = paymentsId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
