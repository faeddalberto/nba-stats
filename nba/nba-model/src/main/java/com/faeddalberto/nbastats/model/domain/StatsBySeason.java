package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.datastax.driver.mapping.annotations.*;
import com.google.common.base.Objects;
import org.springframework.data.cassandra.mapping.CassandraType;

import java.util.Date;
import java.util.UUID;

@Table(name = "stats_by_season", keyspace = "nba")
public class StatsBySeason {

    public StatsBySeason() {}

    public StatsBySeason(
                        int season,
                        int month,
                        String playerTeam,
                        String playerName,
                        String opponentTeam,
                        UUID gameId,
                        UUID playerId,
                        Date date,
                        int minsPlayed,
                        MadeAttemptedStat fieldGoals,
                        MadeAttemptedStat threePoints,
                        MadeAttemptedStat freeThrows,
                        int offensiveRebounds,
                        int defensiveRebounds,
                        int totalRebounds,
                        int assists,
                        int steals,
                        int blocks,
                        int turnovers,
                        int personalFauls,
                        int plusMinus,
                        int points) {
        this.season = season;
        this.month = month;
        this.playerTeam = playerTeam;
        this.playerName = playerName;
        this.opponentTeam = opponentTeam;
        this.gameId = gameId;
        this.playerId = playerId;
        this.date = date;
        this.minsPlayed = minsPlayed;
        this.fieldGoals = fieldGoals;
        this.threePoints = threePoints;
        this.freeThrows = freeThrows;
        this.offensiveRebounds = offensiveRebounds;
        this.defensiveRebounds = defensiveRebounds;
        this.totalRebounds = totalRebounds;
        this.assists = assists;
        this.steals = steals;
        this.blocks = blocks;
        this.turnovers = turnovers;
        this.personalFauls = personalFauls;
        this.plusMinus = plusMinus;
        this.points = points;
    }

    @PartitionKey(0)
    @Column(name = "season")
    private int season;

    @ClusteringColumn(0)
    @Column(name = "month")
    private int month;

    @ClusteringColumn(1)
    @Column(name = "player_team")
    private String playerTeam;

    @ClusteringColumn(2)
    @Column(name = "player_name")
    private String playerName;

    @ClusteringColumn(3)
    @Column(name = "opponent_team")
    private String opponentTeam;

    @ClusteringColumn(4)
    @Column(name = "game_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID gameId;

    @ClusteringColumn(5)
    @Column(name = "player_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID playerId;

    @Column(name = "date")
    private Date date;

    @Column(name = "mins_played")
    private int minsPlayed;

    @Frozen
    @Column(name = "fg_ma")
    private MadeAttemptedStat fieldGoals;

    @Frozen
    @Column(name = "tp_ma")
    private MadeAttemptedStat threePoints;

    @Frozen
    @Column(name = "ft_ma")
    private MadeAttemptedStat freeThrows;

    @Column(name = "off_reb")
    private int offensiveRebounds;

    @Column(name = "def_reb")
    private int defensiveRebounds;

    @Column(name = "tot_reb")
    private int totalRebounds;

    @Column(name = "ast")
    private int assists;

    @Column(name = "stl")
    private int steals;

    @Column(name = "bks")
    private int blocks;

    @Column(name = "tov")
    private int turnovers;

    @Column(name = "pf")
    private int personalFauls;

    @Column(name = "pm")
    private int plusMinus;

    @Column(name = "pts")
    private int points;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getMinsPlayed() {
        return minsPlayed;
    }

    public void setMinsPlayed(int minsPlayed) {
        this.minsPlayed = minsPlayed;
    }

    public MadeAttemptedStat getFieldGoals() {
        return fieldGoals;
    }

    public void setFieldGoals(MadeAttemptedStat fieldGoals) {
        this.fieldGoals = fieldGoals;
    }

    public MadeAttemptedStat getThreePoints() {
        return threePoints;
    }

    public void setThreePoints(MadeAttemptedStat threePoints) {
        this.threePoints = threePoints;
    }

    public MadeAttemptedStat getFreeThrows() {
        return freeThrows;
    }

    public void setFreeThrows(MadeAttemptedStat freeThrows) {
        this.freeThrows = freeThrows;
    }

    public int getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public void setOffensiveRebounds(int offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
    }

    public int getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public void setDefensiveRebounds(int defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
    }

    public int getTotalRebounds() {
        return totalRebounds;
    }

    public void setTotalRebounds(int totalRebounds) {
        this.totalRebounds = totalRebounds;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getSteals() {
        return steals;
    }

    public void setSteals(int steals) {
        this.steals = steals;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(int turnovers) {
        this.turnovers = turnovers;
    }

    public int getPersonalFauls() {
        return personalFauls;
    }

    public void setPersonalFauls(int personalFauls) {
        this.personalFauls = personalFauls;
    }

    public int getPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(int plusMinus) {
        this.plusMinus = plusMinus;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatsBySeason that = (StatsBySeason) o;

        return Objects.equal(this.season, that.season) &&
                Objects.equal(this.month, that.month) &&
                Objects.equal(this.playerTeam, that.playerTeam) &&
                Objects.equal(this.opponentTeam, that.opponentTeam) &&
                Objects.equal(this.gameId, that.gameId) &&
                Objects.equal(this.playerId, that.playerId) &&
                Objects.equal(this.date, that.date) &&
                Objects.equal(this.playerName, that.playerName) &&
                Objects.equal(this.minsPlayed, that.minsPlayed) &&
                Objects.equal(this.fieldGoals, that.fieldGoals) &&
                Objects.equal(this.threePoints, that.threePoints) &&
                Objects.equal(this.freeThrows, that.freeThrows) &&
                Objects.equal(this.offensiveRebounds, that.offensiveRebounds) &&
                Objects.equal(this.defensiveRebounds, that.defensiveRebounds) &&
                Objects.equal(this.totalRebounds, that.totalRebounds) &&
                Objects.equal(this.assists, that.assists) &&
                Objects.equal(this.steals, that.steals) &&
                Objects.equal(this.blocks, that.blocks) &&
                Objects.equal(this.turnovers, that.turnovers) &&
                Objects.equal(this.personalFauls, that.personalFauls) &&
                Objects.equal(this.plusMinus, that.plusMinus) &&
                Objects.equal(this.points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(season, month, playerTeam, opponentTeam, gameId, playerId,
                date, playerName, minsPlayed, fieldGoals, threePoints,
                freeThrows, offensiveRebounds, defensiveRebounds, totalRebounds, assists,
                steals, blocks, turnovers, personalFauls, plusMinus,
                points);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("season", season)
                .add("month", month)
                .add("playerTeam", playerTeam)
                .add("opponentTeam", opponentTeam)
                .add("gameId", gameId)
                .add("playerId", playerId)
                .add("date", date)
                .add("playerName", playerName)
                .add("minsPlayed", minsPlayed)
                .add("fieldGoals", fieldGoals)
                .add("threePoints", threePoints)
                .add("freeThrows", freeThrows)
                .add("offensiveRebounds", offensiveRebounds)
                .add("defensiveRebounds", defensiveRebounds)
                .add("totalRebounds", totalRebounds)
                .add("assists", assists)
                .add("steals", steals)
                .add("blocks", blocks)
                .add("turnovers", turnovers)
                .add("personalFauls", personalFauls)
                .add("plusMinus", plusMinus)
                .add("points", points)
                .toString();
    }
}
