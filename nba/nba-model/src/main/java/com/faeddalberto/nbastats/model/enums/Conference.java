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

    public static Conference fromString(String text) {
        if (text != null) {
            for (Conference conference : Conference.values()) {
                if (text.equalsIgnoreCase(conference.value)) {
                    return conference;
                }
            }
        }
        throw new IllegalArgumentException("Cannot map " + text + " to a valid conference");
    }
}
