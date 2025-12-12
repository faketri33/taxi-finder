package org.faketri.domain.entity;

import dto.CarType;
import dto.address.AddressResponseDto;
import dto.rideStatus.RideStatus;
import org.faketri.domain.exception.RoundCountLimitException;
import org.faketri.usecase.policy.DispatchStatePolicy;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DispatchState {

    private UUID rideId;
    private Set<UUID> driverNotificationSend;
    private AddressResponseDto addressStart;
    private AddressResponseDto addressEnd;
    private CarType carType;
    private RideStatus status;
    private int round = 1;
    private Instant roundExpiresAt;

    public DispatchState(UUID rideId, Set<UUID> driverNotificationSend, AddressResponseDto addressStart, AddressResponseDto addressEnd, CarType carType, RideStatus status) {
        this.rideId = rideId;
        this.driverNotificationSend = driverNotificationSend;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.carType = carType;
        this.status = status;
    }

    public DispatchState() {
    }

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
    }

    public Set<UUID> getDriverNotificationSend() {
        if (driverNotificationSend == null)
            driverNotificationSend = new HashSet<>();
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

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
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

    public void incrementRound(DispatchStatePolicy dispatchStatePolicy) {
        if (dispatchStatePolicy.roundPolicy(this.round + 1))
            throw new RoundCountLimitException("Round must be between 0 and 4", this);
        this.round++;
    }

    public void updateRoundTimeout(){
        this.roundExpiresAt = Instant.now().plusSeconds(190);
    }

    public Instant getRoundExpiresAt() {
        return roundExpiresAt;
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
