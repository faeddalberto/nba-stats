package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;
import com.google.common.base.Objects;

import java.io.Serializable;

@UDT(keyspace = "nba", name = "position")
public class Position implements Serializable {

    public Position(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    @Field(name = "short_name")
    private String shortName;

    @Field(name = "long_name")
    private String longName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("shortName", shortName)
                .add("longName", longName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position that = (Position) o;

        return Objects.equal(this.shortName, that.shortName) &&
                Objects.equal(this.longName, that.longName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shortName, longName);
    }
}
