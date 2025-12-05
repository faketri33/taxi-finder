package org.faketri.domain.dto.request;

import dto.CarType;
import org.faketri.domain.entity.address.model.Address;


public class RideRequestDto {
    private CarType carType;
    private Address startAddress;
    private Address endAddress;

    public RideRequestDto(CarType carType, Address startAddress, Address endAddress) {
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
}
