package org.wm.authservice.domain.mapper;

import org.mapstruct.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.wm.authservice.api.dto.request.UserAuthRequest;
import org.wm.authservice.domain.entity.CustomUserDetails;
import org.wm.authservice.domain.entity.UsersDomain;
import org.wm.authservice.infra.persistence.entity.UsersEntity;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersDomain toDomain(UsersEntity ue);

    UsersDomain toDomain(UserAuthRequest uar);

    UsersEntity toEntity(UsersDomain ud);

    default UserDetails toUserDetails(UsersEntity ud) {
        return new CustomUserDetails(ud.getId(),
                ud.getUsername(),
                ud.getPassword(),
                ud.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                        .collect(Collectors.toSet()),
                ud.getActive()
        );
    }

    default UserDetails toUserDetails(UsersDomain ud) {
        return new CustomUserDetails(ud.getId(),
                ud.getUsername(),
                ud.getPassword(),
                ud.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                        .collect(Collectors.toSet()),
                ud.isActive()
        );
    }
}