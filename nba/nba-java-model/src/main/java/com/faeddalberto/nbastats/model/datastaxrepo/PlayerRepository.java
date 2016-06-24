package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerAccessor;
import com.faeddalberto.nbastats.model.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlayerRepository {

    private MappingManager mappingManager;

    @Autowired
    private CassandraConfig cassandraConfig;

    public List<Player> getAllPlayers() throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerAccessor playerAccessor = mappingManager.createAccessor(PlayerAccessor.class);

        Result<Player> players = playerAccessor.getAllPlayers();

        return players.all();
    }

    public Player getPlayerById(UUID userId) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerAccessor playerAccessor = mappingManager.createAccessor(PlayerAccessor.class);

        Result<Player> players = playerAccessor.getPlayerById(userId);

        return players.one();
    }
}
