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

@Table(value = "game_by_season_teams")
public class GameBySeasonTeams {

    public GameBySeasonTeams(
                            int season,
                            String homeTeam,
                            String visitorTeam,
                            Date date,
                            UUID gameId,
                            int homeTeamScore,
                            int visitorTeamScore,
                            String seasonType) {
        this.season = season;
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
        this.date = date;
        this.gameId = gameId;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeamScore = visitorTeamScore;
        this.seasonType = seasonType;
    }

    @PrimaryKeyColumn(name = "season", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int season;

    @PrimaryKeyColumn(name = "home_team", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String homeTeam;

    @PrimaryKeyColumn(name = "visitor_team", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String visitorTeam;

    @PrimaryKeyColumn(name = "date", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private Date date;

    @PrimaryKeyColumn(name = "game_id", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = DataType.Name.UUID)
    private UUID gameId;

    @Column(value = "home_team_score")
    private int homeTeamScore;

    @Column(value = "visitor_team_score")
    private int visitorTeamScore;

    @Column(value = "season_type")
    private String seasonType;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(int visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameBySeasonTeams that = (GameBySeasonTeams) o;

        return Objects.equal(this.season, that.season) &&
                Objects.equal(this.homeTeam, that.homeTeam) &&
                Objects.equal(this.visitorTeam, that.visitorTeam) &&
                Objects.equal(this.date, that.date) &&
                Objects.equal(this.gameId, that.gameId) &&
                Objects.equal(this.homeTeamScore, that.homeTeamScore) &&
                Objects.equal(this.visitorTeamScore, that.visitorTeamScore) &&
                Objects.equal(this.seasonType, that.seasonType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(season, homeTeam, visitorTeam, date, gameId, homeTeamScore,
                visitorTeamScore, seasonType);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("season", season)
                .add("homeTeam", homeTeam)
                .add("visitorTeam", visitorTeam)
                .add("date", date)
                .add("gameId", gameId)
                .add("homeTeamScore", homeTeamScore)
                .add("visitorTeamScore", visitorTeamScore)
                .add("seasonType", seasonType)
                .toString();
    }
}
