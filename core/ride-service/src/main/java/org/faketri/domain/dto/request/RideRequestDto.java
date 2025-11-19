package org.faketri.domain.dto.request;

import org.faketri.domain.entity.address.model.Address;


public class RideRequestDto {
    private Address startAddress;
    private Address endAddress;

    public RideRequestDto(Address startAddress, Address endAddress) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
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
