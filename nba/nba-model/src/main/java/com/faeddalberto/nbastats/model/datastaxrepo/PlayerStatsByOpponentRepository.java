package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.PlayerStatsByOpponentAccessor;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerStatsByOpponentRepository {

    private MappingManager mappingManager;

    @Autowired
    private CassandraConfig cassandraConfig;

    public List<PlayerStatsByOpponent> getPlayerStatsByOpponent(String opponentTeam, String playerName) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        Result<PlayerStatsByOpponent> playersStats = accessor.getPlayerStatsByOpponent(opponentTeam, playerName);

        return playersStats.all();
    }

    public List<PlayerStatsByOpponent> getPlayerStatsByOpponentAndPlayerTeam(String opponentTeam, String playerName, String playerTeam) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        Result<PlayerStatsByOpponent> playersStats = accessor.getPlayerStatsByOpponentAndPlayerTeam(opponentTeam, playerName, playerTeam);

        return playersStats.all();
    }

    public List<PlayerStatsByOpponent> getPlayerStatsByOpponentAndPlayerTeamAndSeason(String opponentTeam, String playerName, String playerTeam, int season) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        Result<PlayerStatsByOpponent> playersStats = accessor.getPlayerStatsByOpponentAndPlayerTeamAndSeason(opponentTeam, playerName, playerTeam, season);

        return playersStats.all();
    }

    public void deletePlayerStatsByOpponent(String opponentTeam, String playerName) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        accessor.deletePlayerStatsByOpponent(opponentTeam, playerName);
    }

    public void deletePlayerStatsByOpponentAndPlayerTeam(String opponentTeam, String playerName, String playerTeam) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        accessor.deletePlayerStatsByOpponentAndPlayerTeam(opponentTeam, playerName, playerTeam);
    }

    public void deletePlayerStatsByOpponentAndPlayerTeamAndSeason(String opponentTeam, String playerName, String playerTeam, int season) throws Exception {

        mappingManager = new MappingManager(cassandraConfig.session().getObject());

        PlayerStatsByOpponentAccessor accessor = mappingManager.createAccessor(PlayerStatsByOpponentAccessor.class);

        accessor.deletePlayerStatsByOpponentAndPlayerTeamAndSeason(opponentTeam, playerName, playerTeam, season);
    }
}
