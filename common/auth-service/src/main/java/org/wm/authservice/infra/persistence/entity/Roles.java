package org.wm.authservice.infra.persistence.entity;


public enum Roles {

    DEFAULT("Default user"),
    DRIVER("Driver"),
    MODERATOR("Moderator"),
    ADMIN("Administrator");

    private final String name;

    Roles(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }
}
