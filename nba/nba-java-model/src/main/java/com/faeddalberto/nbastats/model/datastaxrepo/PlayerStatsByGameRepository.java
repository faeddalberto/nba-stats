package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerStatsByGameAccessor;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlayerStatsByGameRepository {

    private MappingManager mappingManager;

    @Autowired
    private CassandraConfig cassandraConfig;

    public List<PlayerStatsByGame> getAllGamesStats() throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerStatsByGameAccessor accessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);

        Result<PlayerStatsByGame> playersStats = accessor.getAllGamesStats();

        return playersStats.all();
    }

    public List<PlayerStatsByGame> getGameStats(UUID gameId) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerStatsByGameAccessor accessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);

        Result<PlayerStatsByGame> playersStats = accessor.getGameStats(gameId);

        return playersStats.all();
    }

    public PlayerStatsByGame getPlayerGameStats(UUID gameId, UUID playerId) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerStatsByGameAccessor accessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);

        Result<PlayerStatsByGame> playersStats = accessor.getPlayerGameStats(gameId, playerId);

        return playersStats.one();
    }

    public void deleteGameStats(UUID gameId) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerStatsByGameAccessor accessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);

        accessor.deleteGameStats(gameId);
    }

    public void deletePlayerGameStats(UUID gameId, UUID playerId) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        PlayerStatsByGameAccessor accessor = mappingManager.createAccessor(PlayerStatsByGameAccessor.class);

        accessor.deletePlayerGameStats(gameId, playerId);
    }
}
