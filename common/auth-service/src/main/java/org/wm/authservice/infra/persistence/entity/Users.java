package org.wm.authservice.infra.persistence.entity;

import javax.management.relation.Role;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Users {

    private UUID id;

    private String username;
    private String password;

    private Set<Role> roles;

    private Boolean isActive;

    private Instant createAt;
    private Instant updateAt;

    public Users(UUID id, String username, String password, Set<Role> roles, Boolean isActive, Instant createAt, Instant updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isActive = isActive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Users() {
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
