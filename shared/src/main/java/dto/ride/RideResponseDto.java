package dto.ride;

import dto.address.AddressResponseDto;
import dto.rideStatus.RideStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideResponseDto {
    private UUID id;
    private RideStatus status;
    private AddressResponseDto startAddress;
    private AddressResponseDto endAddress;

    private LocalDateTime createAt;
    private LocalDateTime cancelAt;

    public RideResponseDto(UUID id, RideStatus status, AddressResponseDto startAddress, AddressResponseDto endAddress, LocalDateTime createAt, LocalDateTime cancelAt) {
        this.id = id;
        this.status = status;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.createAt = createAt;
        this.cancelAt = cancelAt;
    }

    public RideResponseDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public AddressResponseDto getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(AddressResponseDto startAddress) {
        this.startAddress = startAddress;
    }

    public AddressResponseDto getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(AddressResponseDto endAddress) {
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
