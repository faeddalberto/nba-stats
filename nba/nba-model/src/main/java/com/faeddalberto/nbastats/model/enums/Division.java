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
}
