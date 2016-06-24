package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.TeamsByConferenceDivision;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamsByConferenceDivisionRepository extends CassandraRepository<TeamsByConferenceDivision> {

    @Query("SELECT * FROM nba.teams_by_conference_division WHERE conference =?0")
    List<TeamsByConferenceDivision> findTeamsByConference(String conference);

    @Query("SELECT * FROM nba.teams_by_conference_division WHERE conference =?0 AND division =?1")
    List<TeamsByConferenceDivision> findTeamsByConferenceAndDivision(String conference, String division);
}