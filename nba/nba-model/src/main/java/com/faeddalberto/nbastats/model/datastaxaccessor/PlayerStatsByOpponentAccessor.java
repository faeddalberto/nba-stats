package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;

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
}
