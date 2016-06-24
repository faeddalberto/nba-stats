package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.Team;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface TeamRepository extends CassandraRepository<Team> {

    @Query("SELECT * FROM nba.team WHERE team_id=?0")
    Team findTeamById(String teamId);

}
