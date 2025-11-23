package org.faketri.entity.device.gateway;

import org.faketri.entity.device.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {
}
