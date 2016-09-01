package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.*;
import com.faeddalberto.nbastats.model.enums.Role;
import com.google.common.base.Objects;
import org.springframework.data.cassandra.mapping.CassandraType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(name = "player_career_by_name", keyspace = "nba")
public class PlayerCareerByName implements Serializable {

    public PlayerCareerByName() {}

    public PlayerCareerByName(
            String name,
            int year,
            String team,
            UUID playerId,
            String country,
            Date dateOfBirth,
            String draftedInfo,
            Role role) {
        this.name = name;
        this.year = year;
        this.team = team;
        this.playerId = playerId;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.draftedInfo = draftedInfo;
        this.role = role;
    }

    @PartitionKey(0)
    @Column(name = "name")
    private String name;

    @ClusteringColumn(0)
    @Column(name = "year")
    private int year;

    @ClusteringColumn(1)
    @Column(name = "team")
    private String team;

    @ClusteringColumn(2)
    @Column(name = "player_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID playerId;

    @Column(name = "country")
    private String country;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "drafted")
    private String draftedInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDraftedInfo() {
        return draftedInfo;
    }

    public void setDraftedInfo(String draftedInfo) {
        this.draftedInfo = draftedInfo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerCareerByName that = (PlayerCareerByName) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.year, that.year) &&
                Objects.equal(this.team, that.team) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.country, that.country) &&
                Objects.equal(this.dateOfBirth, that.dateOfBirth) &&
                Objects.equal(this.draftedInfo, that.draftedInfo) &&
                Objects.equal(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, year, team, playerId, country, dateOfBirth,
                draftedInfo, role);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("year", year)
                .add("team", team)
                .add("playerId", playerId)
                .add("country", country)
                .add("dateOfBirth", dateOfBirth)
                .add("draftedInfo", draftedInfo)
                .add("role", role)
                .toString();
    }
}
