package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;
import com.faeddalberto.nbastats.model.enums.Role;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface PlayerCareerByNameAccessor {

    @Query("SELECT * FROM nba.player_career_by_name WHERE name = :name")
    Result<PlayerCareerByName> getPlayerCareerByName(@Param("name") String name);

    @Query("SELECT * FROM nba.player_career_by_name WHERE name = :name AND year = :year")
    Result<PlayerCareerByName> getPlayerInfoByNameAndYear(@Param("name") String name, @Param("year") int year);

    @Query("DELETE FROM nba.player_career_by_name WHERE name = :name")
    void deletePlayerCareer(@Param("name") String name);

    @Query("INSERT INTO nba.player_career_by_name (name, year, team, player_id, country, dob, drafted, role) VALUES (:name, :year, :team, :player_id, :country, :dob, :drafted, :role)")
    Statement insertPlayerCareer(@Param("name") String name, @Param("year") int year, @Param("team") String team, @Param("player_id") UUID playeId, @Param("country") String country, @Param("dob") Date dateOfBirth, @Param("drafted") String draftedInfo, @Param("role") Role role);
}
