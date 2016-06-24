package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;
import org.springframework.data.cassandra.mapping.CassandraType;

import java.util.Date;
import java.util.UUID;

@Table(name = "player", keyspace = "nba")
public class Player {

    public Player() { }

    public Player(UUID playerId, Date dateOfBirth, String name, String country, String draftedInfo) {
        this.playerId = playerId;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.country = country;
        this.draftedInfo = draftedInfo;
    }

    @PartitionKey(0)
    @Column(name = "player_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID playerId;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "drafted")
    private String draftedInfo;

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDraftedInfo() {
        return draftedInfo;
    }

    public void setDraftedInfo(String draftedInfo) {
        this.draftedInfo = draftedInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player that = (Player) o;

        return Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.dateOfBirth, that.dateOfBirth) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.country, that.country) &&
                Objects.equal(this.draftedInfo, that.draftedInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerId, dateOfBirth, name, country, draftedInfo);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", dateOfBirth=" + dateOfBirth +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", draftedInfo='" + draftedInfo + '\'' +
                '}';
    }
}
