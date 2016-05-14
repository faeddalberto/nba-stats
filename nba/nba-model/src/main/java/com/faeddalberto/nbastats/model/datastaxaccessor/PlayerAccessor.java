package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.Player;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface PlayerAccessor {

    @Query("SELECT * FROM nba.player")
    Result<Player> getAllPlayers();

    @Query("SELECT * FROM nba.player WHERE player_id = :id")
    Result<Player> getPlayerById(@Param("id") UUID userId);

    @Query("INSERT INTO nba.player (player_id, dob, name, country, drafted) VALUES (:player_id, :dob, :name, :country, :drafted);")
    Statement insertPlayer(@Param("player_id") UUID player_id, @Param("dob") Date dob, @Param("name") String name, @Param("country") String country, @Param("drafted") String draftedInfo);
}
