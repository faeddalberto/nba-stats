package com.faeddalberto.nbastats.model.domain;

import com.datastax.driver.core.DataType;
import com.datastax.driver.mapping.annotations.*;
import com.google.common.base.Objects;
import org.springframework.data.cassandra.mapping.CassandraType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(name = "player_stats_by_opponent", keyspace = "nba")
public class PlayerStatsByOpponent implements Serializable {

    public PlayerStatsByOpponent() {}

    public PlayerStatsByOpponent(
                                String opponentTeam,
                                String playerName,
                                String playerTeam,
                                int season,
                                Date date,
                                UUID gameId,
                                UUID playerId,
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
        this.opponentTeam = opponentTeam;
        this.playerName = playerName;
        this.playerTeam = playerTeam;
        this.season = season;
        this.date = date;
        this.gameId = gameId;
        this.playerId = playerId;
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
    @Column(name = "opponent_team")
    private String opponentTeam;

    @PartitionKey(1)
    @Column(name = "player_name")
    private String playerName;

    @ClusteringColumn(0)
    @Column(name = "player_team")
    private String playerTeam;

    @ClusteringColumn(1)
    @Column(name = "season")
    private int season;

    @ClusteringColumn(2)
    @Column(name = "date")
    private Date date;

    @ClusteringColumn(3)
    @Column(name = "game_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID gameId;

    @ClusteringColumn(4)
    @Column(name = "player_id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID playerId;

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

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
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

        PlayerStatsByOpponent that = (PlayerStatsByOpponent) o;

        return Objects.equal(this.opponentTeam, that.opponentTeam) &&
                Objects.equal(this.playerName, that.playerName) &&
                Objects.equal(this.playerTeam, that.playerTeam) &&
                Objects.equal(this.season, that.season) &&
                Objects.equal(this.date, that.date) &&
                Objects.equal(this.gameId, that.gameId) &&
                Objects.equal(this.playerId, that.playerId) &&
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
        return Objects.hashCode(opponentTeam, playerName, playerTeam, season, date, gameId,
                playerId, minsPlayed, fieldGoals, threePoints, freeThrows,
                offensiveRebounds, defensiveRebounds, totalRebounds, assists, steals,
                blocks, turnovers, personalFauls, plusMinus, points);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("opponentTeam", opponentTeam)
                .add("playerName", playerName)
                .add("playerTeam", playerTeam)
                .add("season", season)
                .add("date", date)
                .add("gameId", gameId)
                .add("playerId", playerId)
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
