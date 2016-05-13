package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;

@Accessor
public interface PlayerCareerByNameAccessor {

    @Query("SELECT * FROM nba.player_career_by_name WHERE name = :name")
    Result<PlayerCareerByName> getPlayerCareerByName(@Param("name") String name);

    @Query("SELECT * FROM nba.player_career_by_name WHERE name = :name AND year = :year")
    Result<PlayerCareerByName> getPlayerInfoByNameAndYear(@Param("name") String name, @Param("year") int year);

    @Query("DELETE FROM nba.player_career_by_name WHERE name = :name")
    void deletePlayerCareer(@Param("name") String name);

}
