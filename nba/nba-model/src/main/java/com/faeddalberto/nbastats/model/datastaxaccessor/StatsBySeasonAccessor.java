package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface StatsBySeasonAccessor {

    @Query("SELECT * FROM nba.stats_by_season WHERE season = :season")
    Result<StatsBySeason> getLeagueStatsBySeason(@Param("season") int season);

    @Query("SELECT * FROM nba.stats_by_season WHERE season = :season AND month = :month")
    Result<StatsBySeason> getLeagueStatsBySeasonAndMonth(@Param("season") int year, @Param("month") int month);

    @Query("SELECT * FROM nba.stats_by_season WHERE season = :season AND month = :month AND player_team = :player_team")
    Result<StatsBySeason> getTeamStatsBySeasonAndMonth(@Param("season") int season, @Param("month") int month, @Param("player_team") String playerTeam);

    @Query("SELECT * FROM nba.stats_by_season WHERE season = :season AND month = :month AND player_team = :player_team AND player_name = :player_name")
    Result<StatsBySeason> getPlayerStatsBySeasonAndMonth(@Param("season") int year, @Param("month") int month, @Param("player_team") String playerTeam, @Param("player_name") String playerName);

    @Query("DELETE FROM nba.stats_by_season WHERE season = :season")
    void deleteLeagueStatsBySeason(@Param("season") int year);

    @Query("DELETE FROM nba.stats_by_season WHERE season = :season AND month = :month")
    void deleteLeagueStatsBySeasonAndMonth(@Param("season") int season, @Param("month") int month);

    @Query("DELETE FROM nba.stats_by_season WHERE season = :season AND month = :month AND player_team = :player_team")
    void deleteTeamStatsBySeasonAndMonth(@Param("season") int year, @Param("month") int month, @Param("player_team") String playerTeam);

    @Query("DELETE FROM nba.stats_by_season WHERE season = :season AND month = :month AND player_team = :player_team AND player_name = :player_name")
    void deletePlayerStatsBySeasonAndMonth(@Param("season") int season, @Param("month") int month, @Param("player_team") String playerTeam, @Param("player_name") String playerName);

    @Query("INSERT INTO nba.stats_by_season (season, month, player_team, player_name, opponent_team, game_id, player_id, date, mins_played, fg_ma, tp_ma, ft_ma, off_reb, def_reb, tot_reb, ast, stl, bks, tov, pf, pm, pts) VALUES (:season, :month, :player_team, :player_name, :opponent_team, :game_id, :player_id, :date, :mins_played, :fg_ma, :tp_ma, :ft_ma, :off_reb, :def_reb, :tot_reb, :ast, :stl, :bks, :tov, :pf, :pm, :pts)")
    Statement insertStatsBySeason(@Param("season") int season, @Param("month") int month, @Param("player_team") String playerTeam, @Param("player_name") String playerName, @Param("opponent_team") String opponentTeam, @Param("game_id") UUID gameId, @Param("player_id") UUID playerId, @Param("date") Date date, @Param("mins_played") int minsPlayed, @Param("fg_ma") MadeAttemptedStat fieldGoals, @Param("tp_ma") MadeAttemptedStat threePoints, @Param("ft_ma") MadeAttemptedStat freeThrows, @Param("off_reb") int offReb, @Param("def_reb") int defReb, @Param("tot_reb") int totReb, @Param("ast") int assists, @Param("stl") int steals, @Param("bks") int blocks, @Param("tov") int turnovers, @Param("pf") int personalFauls, @Param("pm") int plusMinus, @Param("pts") int points);
}
