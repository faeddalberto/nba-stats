package com.faeddalberto.nbastats.model.enums;

public enum SeasonType {

    REGULAR_SEASON("Regular Season"),
    PLAYOFFS("Playoffs");

    SeasonType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public static SeasonType fromString(String text) {
        if (text != null) {
            for (SeasonType seasonType : SeasonType.values()) {
                if (seasonType.value.contains(text)) {
                    return seasonType;
                }
            }
        }
        throw new IllegalArgumentException("Cannot map " + text + " to a valid season type");
    }
}
