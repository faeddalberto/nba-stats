package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.TeamRosterByYearPlayer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamRosterByYearRepository extends CassandraRepository<TeamRosterByYearPlayer> {

    @Query("SELECT * FROM nba.team_roster_by_year WHERE year =?0 and team =?1")
    List<TeamRosterByYearPlayer> findTeamRosterByYear(int year, String team);
}
