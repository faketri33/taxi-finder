package org.wm.authservice.domain.mapper;

import org.mapstruct.Mapper;
import org.wm.authservice.api.dto.UserAuthRequest;
import org.wm.authservice.domain.entity.UsersDomain;
import org.wm.authservice.infra.persistence.entity.UsersEntity;

@Mapper(componentModel = "spring")
public interface UsersMapper { 

    UsersDomain toDomain(UsersEntity ue);
    UsersDomain toDomain(UserAuthRequest uar);
    UsersEntity toEntity(UsersDomain ud);
    
}