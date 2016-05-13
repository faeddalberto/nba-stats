package com.faeddalberto.nbastats.model.datastaxaccessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;

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

}
