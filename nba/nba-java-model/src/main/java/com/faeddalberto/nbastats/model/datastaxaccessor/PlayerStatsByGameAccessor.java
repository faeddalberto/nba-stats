package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface PlayerStatsByGameAccessor {

    @Query("SELECT * FROM nba.player_stats_by_game")
    Result<PlayerStatsByGame> getAllGamesStats();

    @Query("SELECT * FROM nba.player_stats_by_game WHERE game_id = :id")
    Result<PlayerStatsByGame> getGameStats(@Param("id") UUID gameId);

    @Query("SELECT * FROM nba.player_stats_by_game WHERE game_id = :game_id AND player_id = :player_id")
    Result<PlayerStatsByGame> getPlayerGameStats(@Param("game_id") UUID gameId, @Param("player_id") UUID playerId);

    @Query("DELETE FROM nba.player_stats_by_game WHERE game_id = :id")
    void deleteGameStats(@Param("id") UUID gameId);

    @Query("DELETE FROM nba.player_stats_by_game WHERE game_id = :game_id AND player_id = :player_id")
    void deletePlayerGameStats(@Param("game_id") UUID gameId, @Param("player_id") UUID playerId);

    @Query("INSERT INTO nba.player_stats_by_game (game_id, player_id, date, season, player_team, opponent_team, mins_played, player_name, fg_ma, tp_ma, ft_ma, off_reb, def_reb, tot_reb, ast, stl, bks, tov, pf, pm, pts) VALUES (:game_id, :player_id, :date, :season, :player_team, :opponent_team, :mins_played, :player_name, :fg_ma, :tp_ma, :ft_ma, :off_reb, :def_reb, :tot_reb, :ast, :stl, :bks, :tov, :pf, :pm, :pts)")
    Statement insertPlayerStatsByGame(@Param("game_id") UUID gameId, @Param("player_id") UUID playerId, @Param("date") Date date, @Param("season") int season, @Param("player_team") String playerTeam, @Param("opponent_team") String opponentTeam, @Param("mins_played") int minsPlayed, @Param("player_name") String playerName, @Param("fg_ma") MadeAttemptedStat fieldGoals, @Param("tp_ma") MadeAttemptedStat threePoints, @Param("ft_ma") MadeAttemptedStat freeThrows, @Param("off_reb") int offReb, @Param("def_reb") int defReb, @Param("tot_reb") int totalReb, @Param("ast") int assists, @Param("stl") int steals, @Param("bks") int blocks, @Param("tov") int turnovers, @Param("pf") int personalFauls, @Param("pm") int plusMinus, @Param("pts") int points);
}
