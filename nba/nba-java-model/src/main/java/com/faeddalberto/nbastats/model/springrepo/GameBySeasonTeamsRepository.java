package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.GameBySeasonTeams;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Date;
import java.util.List;

public interface GameBySeasonTeamsRepository extends CassandraRepository<GameBySeasonTeams> {

    @Query("SELECT * FROM nba.game_by_season_teams WHERE season=?0 AND date = ?1 AND home_team=?2 AND visitor_team=?3")
    List<GameBySeasonTeams> findBySeasonDateHomeTeamVisitorTeam(
                    int season,Date date, String homeTeam, String visitorTeam);

    @Query("SELECT * FROM nba.game_by_season_teams WHERE season=?0 AND date=?1")
    List<GameBySeasonTeams> findBySeasonAndDate(
                    int season, Date date);

}
