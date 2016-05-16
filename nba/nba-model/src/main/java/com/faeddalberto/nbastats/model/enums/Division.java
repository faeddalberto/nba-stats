package com.faeddalberto.nbastats.model.enums;

public enum Division {

    //EASTERN CONFERENCE
    ATLANTIC_DIVISION("Atlantic Division"),
    CENTRAL_DIVISION("Central Division"),
    SOUTHEAST_DIVISION("Southeast Division"),

    //WESTERN CONFERENCE
    PACIFIC_DIVISION("Pacific Division"),
    SOUTHWEST_DIVISION("Southwest Division"),
    NORTHWEST_DIVISION("Northwest Division");

    Division(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public static Division fromString(String text) {
        if (text != null) {
            for (Division division : Division.values()) {
                if (text.equalsIgnoreCase(division.value)) {
                    return division;
                }
            }
        }
        throw new IllegalArgumentException("Cannot map " + text + " to a valid division");
    }
}
