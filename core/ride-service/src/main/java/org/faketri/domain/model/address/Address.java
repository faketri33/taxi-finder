package org.faketri.domain.model.address;

import java.util.UUID;

public class Address {
    private UUID id;

    private Double latitude;
    private Double longitude;
    private String fullname;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        if (latitude < -90.0 && latitude > 90.0)
            throw new IllegalArgumentException("latitude dont can be < -90 or > 90");
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        if (longitude < -180.0 && longitude > 180.0)
            throw new IllegalArgumentException("longitude dont can be < -180 or > 180");
        this.longitude = longitude;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
