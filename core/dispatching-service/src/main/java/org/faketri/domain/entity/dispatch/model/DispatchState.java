package org.faketri.domain.entity.dispatch.model;

import dto.CarType;
import dto.address.AddressResponseDto;
import dto.rideStatus.RideStatus;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@RedisHash("dispatch_state")
public class DispatchState {
    private UUID rideId;
    private Set<UUID> driverNotificationSend;
    private AddressResponseDto addressStart;
    private AddressResponseDto addressEnd;
    private CarType carType;
    private RideStatus status;
    private int round;
    private Instant roundExpiresAt;

    public DispatchState(UUID id, Set<UUID> driverNotificationSend, AddressResponseDto addressStart, AddressResponseDto addressEnd, CarType carType, RideStatus status) {
        this.rideId = id;
        this.driverNotificationSend = driverNotificationSend;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.status = status;
        this.carType = carType;
        this.round = 1;
    }

    public DispatchState() {
    }

    public UUID getId() {
        return rideId;
    }

    public void setId(UUID id) {
        this.rideId = id;
    }

    public Set<UUID> getDriverNotificationSend() {
        return driverNotificationSend;
    }

    public void setDriverNotificationSend(Set<UUID> driverNotificationSend) {
        this.driverNotificationSend = driverNotificationSend;
    }

    public AddressResponseDto getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(AddressResponseDto addressStart) {
        this.addressStart = addressStart;
    }

    public AddressResponseDto getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(AddressResponseDto addressEnd) {
        this.addressEnd = addressEnd;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Instant getRoundExpiresAt() {
        return roundExpiresAt;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void setRoundExpiresAt(Instant roundExpiresAt) {
        this.roundExpiresAt = roundExpiresAt;
    }

    @Override
    public String toString() {
        return "DispatchState{" +
                "rideId=" + rideId +
                ", driverNotificationSend=" + driverNotificationSend +
                ", addressStart=" + addressStart +
                ", addressEnd=" + addressEnd +
                ", carType=" + carType +
                ", status=" + status +
                ", round=" + round +
                ", roundExpiresAt=" + roundExpiresAt +
                '}';
    }
}
