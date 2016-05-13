package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.GameBySeasonTeams;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Date;

public interface GameBySeasonTeamsRepository extends CassandraRepository<GameBySeasonTeams> {

    @Query("SELECT * FROM nba.game_by_season_teams WHERE season=?0 AND home_team=?1 AND visitor_team=?2")
    Iterable<GameBySeasonTeams> findBySeasonHomeTeamVisitorTeam(
                    int season,String homeTeam, String visitorTeam);

    @Query("SELECT * FROM nba.game_by_season_teams WHERE season=?0 AND home_team=?1 AND visitor_team=?2 AND date=?3")
    Iterable<GameBySeasonTeams> findBySeasonHomeTeamVisitorTeamDate(
                    int season,String homeTeam, String visitorTeam, Date date);

}
