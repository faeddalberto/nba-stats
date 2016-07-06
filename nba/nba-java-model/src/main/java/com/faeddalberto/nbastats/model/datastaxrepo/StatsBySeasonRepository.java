package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxaccessor.StatsBySeasonAccessor;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatsBySeasonRepository {

    private MappingManager mappingManager;

    @Autowired
    private CassandraConfig cassandraConfig;

    public List<StatsBySeason> getLeagueStatsBySeason(int season) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        Result<StatsBySeason> playersStats = accessor.getLeagueStatsBySeason(season);

        return playersStats.all();
    }

    public List<StatsBySeason> getLeagueStatsBySeasonAndMonth(int season, int month) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        Result<StatsBySeason> playersStats = accessor.getLeagueStatsBySeasonAndMonth(season, month);

        return playersStats.all();
    }

    public List<StatsBySeason> getTeamStatsBySeasonAndMonth(int season, int month, String playerTeam) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        Result<StatsBySeason> playersStats = accessor.getTeamStatsBySeasonAndMonth(season, month, playerTeam);

        return playersStats.all();
    }

    public List<StatsBySeason> getPlayerStatsByYearAndMonth(int year, int month, String playerTeam, String playerName) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        Result<StatsBySeason> playersStats = accessor.getPlayerStatsBySeasonAndMonth(year, month, playerTeam, playerName);

        return playersStats.all();
    }

    public void deleteLeagueStatsBySeason(int season) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        accessor.deleteLeagueStatsBySeason(season);
    }

    public void deleteLeagueStatsBySeasonAndMonth(int season, int month) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        accessor.deleteLeagueStatsBySeasonAndMonth(season, month);
    }

    public void deleteTeamStatsBySeasonAndMonth(int season, int month, String playerTeam) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        accessor.deleteTeamStatsBySeasonAndMonth(season, month, playerTeam);
    }

    public void deletePlayerStatsBySeasonAndMonth( int season, int month, String playerTeam, String playerName) throws Exception {

        mappingManager = cassandraConfig.getMappingManager();

        StatsBySeasonAccessor accessor = mappingManager.createAccessor(StatsBySeasonAccessor.class);

        accessor.deletePlayerStatsBySeasonAndMonth(season, month, playerTeam, playerName);
    }
}
