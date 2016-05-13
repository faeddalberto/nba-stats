package com.faeddalberto.nbastats.model.domain;

import com.google.common.base.Objects;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "team")
public class Team {

    public Team() {}

    public Team(
                String teamId,
                String name,
                String conference,
                String division) {
        this.teamId = teamId;
        this.name = name;
        this.conference = conference;
        this.division = division;
    }

    @PrimaryKeyColumn(name = "team_id", ordinal =  0, type = PrimaryKeyType.PARTITIONED)
    private String teamId;

    @Column(value = "name")
    private String name;

    @Column(value = "conference")
    private String conference;

    @Column(value = "division")
    private String division;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team that = (Team) o;

        return Objects.equal(this.teamId, that.teamId) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.conference, that.conference) &&
                Objects.equal(this.division, that.division);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(teamId, name, conference, division);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("teamId", teamId)
                .add("name", name)
                .add("conference", conference)
                .add("division", division)
                .toString();
    }
}
