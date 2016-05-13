package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.domain.Game;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.UUID;

public interface GameRepository extends CassandraRepository<Game> {

    @Query("SELECT * FROM nba.game WHERE game_id=?0")
    Game findById(UUID gameId);
}
