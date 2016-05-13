package com.faeddalberto.nbastats.model.enums;

public enum Conference {

    EASTERN_CONFERENCE("Eastern Conference"),
    WESTERN_CONFERENCE("Western Conference");

    Conference(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
