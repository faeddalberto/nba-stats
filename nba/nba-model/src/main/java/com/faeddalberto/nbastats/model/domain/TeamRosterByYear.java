package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.google.common.base.Objects;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

@Table(value = "team_roster_by_year")
public class TeamRosterByYear {

    public TeamRosterByYear() {}

    public TeamRosterByYear(
                            int year,
                            String team,
                            UUID playerId,
                            String playerName,
                            String role) {
        this.year = year;
        this.team = team;
        this.playerId = playerId;
        this.playerName = playerName;
        this.role = role;
    }

    @PrimaryKeyColumn(name = "year", ordinal =  0, type = PrimaryKeyType.PARTITIONED)
    private int year;

    @PrimaryKeyColumn(name = "team", ordinal =  1, type = PrimaryKeyType.PARTITIONED)
    private String team;

    @PrimaryKeyColumn(name = "player_id", ordinal =  2, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.UUID)
    private UUID playerId;

    @Column(value = "player_name")
    private String playerName;

    @Column(value = "role")
    private String role;

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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamRosterByYear that = (TeamRosterByYear) o;

        return Objects.equal(this.year, that.year) &&
                Objects.equal(this.team, that.team) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.playerName, that.playerName) &&
                Objects.equal(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(year, team, playerId, playerName, role);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("year", year)
                .add("team", team)
                .add("playerId", playerId)
                .add("playerName", playerName)
                .add("role", role)
                .toString();
    }
}
