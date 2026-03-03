package org.faketri.domain.entity.mapper;

import org.faketri.api.dto.ProfileResponseDto;
import org.faketri.domain.entity.driver.Profile;
import org.faketri.infrastructure.persistence.entity.ProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toDomain(ProfileEntity pe);
    ProfileEntity toEntity(Profile p);
    ProfileResponseDto toDto(Profile p);
}
