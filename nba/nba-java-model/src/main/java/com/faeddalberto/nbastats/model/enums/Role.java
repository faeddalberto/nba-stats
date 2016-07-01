package com.faeddalberto.nbastats.model.enums;

public enum Role {

    GUARD("G"),
    POINT_GUARD("PG"),
    SHOOTING_GUARD("SG"),
    GUARD_FORWARD("GF"),
    FORWARD("F"),
    POWER_FORWARD("PF"),
    SMALL_FORWARD("SF"),
    FORWARD_CENTER("FC"),
    CENTER("C");

    Role(String shortName) {
        this.shortName = shortName;
    }

    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public static Role fromShortName(String shortName) {
        for (Role role : values()) {
            if (role.shortName.equals(shortName))
                return role;
        }

        throw new IllegalArgumentException("Role " + shortName + " doesn't exist");
    }
}
