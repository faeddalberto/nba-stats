package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.google.common.base.Objects;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "teams_by_conference_division")
public class TeamsByConferenceDivision {

    public TeamsByConferenceDivision() {}

    public TeamsByConferenceDivision(String conference, String division, String teamId, String name) {
        this.conference = conference;
        this.division = division;
        this.teamId = teamId;
        this.name = name;
    }

    @CassandraType(type = DataType.Name.TEXT)
    @PrimaryKeyColumn(name = "conference", ordinal =  0, type = PrimaryKeyType.PARTITIONED)
    private String conference;

    @CassandraType(type = DataType.Name.TEXT)
    @PrimaryKeyColumn(name = "division",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private String division;

    @Column(value = "team_id")
    private String teamId;

    @Column(value = "name")
    private String name;

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamsByConferenceDivision that = (TeamsByConferenceDivision) o;

        return Objects.equal(this.conference, that.conference) &&
                Objects.equal(this.division, that.division) &&
                Objects.equal(this.teamId, that.teamId) &&
                Objects.equal(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(conference, division, teamId, name);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("conference", conference)
                .add("division", division)
                .add("teamId", teamId)
                .add("name", name)
                .toString();
    }
}
