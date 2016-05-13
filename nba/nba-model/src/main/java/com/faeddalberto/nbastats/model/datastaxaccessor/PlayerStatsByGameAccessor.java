package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;

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
}
