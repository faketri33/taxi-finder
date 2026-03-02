package org.wm.authservice.domain.entity;

import org.wm.authservice.infra.persistence.entity.Roles;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class UsersDomain {

    private UUID id;

    private String username;
    private String password;

    private Set<Roles> roles = new HashSet<>();

    private Boolean isActive;

    private Instant createAt;
    private Instant updateAt;

    public UsersDomain(UUID id, String username, String password, Set<Roles> roles, Boolean isActive, Instant createAt,
                       Instant updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public UsersDomain() {

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }


}
