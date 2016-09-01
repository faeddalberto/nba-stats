package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;
import com.google.common.base.Objects;

import java.io.Serializable;

@UDT(name = "made_attempted_stat", keyspace = "nba")
public class MadeAttemptedStat implements Serializable {

    public MadeAttemptedStat() {}

    public MadeAttemptedStat(int made, int attempted) {
        this.made = made;
        this.attempted = attempted;
    }

    @Field
    private int made;

    @Field
    private int attempted;

    public int getMade() {
        return made;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setMade(int made) {
        this.made = made;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }



    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("made", made)
                .add("attempted", attempted)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MadeAttemptedStat that = (MadeAttemptedStat) o;

        return Objects.equal(this.made, that.made) &&
                Objects.equal(this.attempted, that.attempted);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(made, attempted);
    }
}
