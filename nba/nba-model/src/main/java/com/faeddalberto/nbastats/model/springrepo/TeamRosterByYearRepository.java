package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.TeamRosterByYear;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamRosterByYearRepository extends CassandraRepository<TeamRosterByYear> {

    @Query("SELECT * FROM nba.team_roster_by_year WHERE year =?0 and team =?1")
    List<TeamRosterByYear> findTeamRosterByYear(int year, String team);
}
