package org.faketri.api.dto.request;

import dto.CarType;
import org.faketri.infrastructure.persistence.entity.address.model.AddressEntity;


public class RideRequestDto {
    private CarType carType;
    private AddressEntity startAddress;
    private AddressEntity endAddress;

    public RideRequestDto(CarType carType, AddressEntity startAddress, AddressEntity endAddress) {
        this.carType = carType;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
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
}
