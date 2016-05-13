package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.google.common.base.Objects;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "game")
public class Game {

    public Game(
            UUID gameId,
            int season,
            Date date,
            String seasonType,
            String homeTeam,
            int homeTeamScore,
            String visitorTeam,
            int visitorTeamScore) {
        this.gameId = gameId;
        this.season = season;
        this.date = date;
        this.seasonType = seasonType;
        this.homeTeam = homeTeam;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeam = visitorTeam;
        this.visitorTeamScore = visitorTeamScore;
    }

    @PrimaryKeyColumn(name = "game_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.UUID)
    private UUID gameId;

    @Column(value = "season")
    private int season;

    @Column(value = "date")
    private Date date;

    @Column(value = "season_type")
    private String seasonType;

    @Column(value = "home_team")
    private String homeTeam;

    @Column(value = "home_team_score")
    private int homeTeamScore;

    @Column(value = "visitor_team")
    private String visitorTeam;

    @Column(value = "visitor_team_score")
    private int visitorTeamScore;

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game that = (Game) o;

        return Objects.equal(this.gameId, that.gameId) &&
                Objects.equal(this.season, that.season) &&
                Objects.equal(this.date, that.date) &&
                Objects.equal(this.seasonType, that.seasonType) &&
                Objects.equal(this.homeTeam, that.homeTeam) &&
                Objects.equal(this.homeTeamScore, that.homeTeamScore) &&
                Objects.equal(this.visitorTeam, that.visitorTeam) &&
                Objects.equal(this.visitorTeamScore, that.visitorTeamScore);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameId, season, date, seasonType, homeTeam, homeTeamScore,
                visitorTeam, visitorTeamScore);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("gameId", gameId)
                .add("season", season)
                .add("date", date)
                .add("seasonType", seasonType)
                .add("homeTeam", homeTeam)
                .add("homeTeamScore", homeTeamScore)
                .add("visitorTeam", visitorTeam)
                .add("visitorTeamScore", visitorTeamScore)
                .toString();
    }
}
