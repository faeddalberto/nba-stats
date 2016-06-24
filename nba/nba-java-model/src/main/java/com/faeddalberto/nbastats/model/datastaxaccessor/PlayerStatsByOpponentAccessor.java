package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface PlayerStatsByOpponentAccessor {

    @Query("SELECT * FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name")
    Result<PlayerStatsByOpponent> getPlayerStatsByOpponent(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName);

    @Query("SELECT * FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name and player_team = :player_team")
    Result<PlayerStatsByOpponent> getPlayerStatsByOpponentAndPlayerTeam(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName, @Param("player_team") String playerTeam);

    @Query("SELECT * FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name and player_team = :player_team and season = :season")
    Result<PlayerStatsByOpponent> getPlayerStatsByOpponentAndPlayerTeamAndSeason(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName, @Param("player_team") String playerTeam, @Param("season") int season);

    @Query("DELETE FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name")
    void deletePlayerStatsByOpponent(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName);

    @Query("DELETE FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name and player_team = :player_team")
    void deletePlayerStatsByOpponentAndPlayerTeam(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName, @Param("player_team") String playerTeam);

    @Query("DELETE FROM nba.player_stats_by_opponent WHERE opponent_team = :opponent_team AND player_name = :player_name and player_team = :player_team and season = :season")
    void deletePlayerStatsByOpponentAndPlayerTeamAndSeason(@Param("opponent_team") String opponentTeam, @Param("player_name") String playerName, @Param("player_team") String playerTeam, @Param("season") int season);

    @Query("INSERT INTO nba.player_stats_by_opponent (opponent_team, player_name, player_team, season, date, game_id, player_id, mins_played, fg_ma, tp_ma, ft_ma, off_reb, def_reb, tot_reb, ast, stl, bks, tov, pf, pm, pts) VALUES (:opponent_team, :player_name, :player_team, :season, :date, :game_id, :player_id, :mins_played, :fg_ma, :tp_ma, :ft_ma, :off_reb, :def_reb, :tot_reb, :ast, :stl, :bks, :tov, :pf, :pm, :pts)")
    Statement insertplayerStatsByOpponent(@Param("opponent_team") String opponentTeam, @Param("player_name") String player_name, @Param("player_team") String playerTeam, @Param("season") int season, @Param("date") Date date, @Param("game_id") UUID gameId, @Param("player_id") UUID playerId, @Param("mins_played") int minsPlayed, @Param("fg_ma") MadeAttemptedStat fieldGoals, @Param("tp_ma") MadeAttemptedStat threePoints, @Param("ft_ma") MadeAttemptedStat freeThrows, @Param("off_reb") int offReb, @Param("def_reb") int defReb, @Param("tot_reb") int totReb, @Param("ast") int assists, @Param("stl") int steals, @Param("bks") int blocks, @Param("tov") int turnovers, @Param("pf") int personalFauls, @Param("pm") int plusMinus, @Param("pts") int points);
}
