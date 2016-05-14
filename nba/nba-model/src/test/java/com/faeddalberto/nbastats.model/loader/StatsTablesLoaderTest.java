package com.faeddalberto.nbastats.model.loader;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxrepo.PlayerStatsByGameRepository;
import com.faeddalberto.nbastats.model.datastaxrepo.PlayerStatsByOpponentRepository;
import com.faeddalberto.nbastats.model.datastaxrepo.StatsBySeasonRepository;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatsTablesLoaderTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME_1 = "player_stats_by_game";
    public static final String DATA_TABLE_NAME_2 = "player_stats_by_opponent";
    public static final String DATA_TABLE_NAME_3 = "stats_by_season";

    @Autowired
    private StatsTablesLoader statsTablesLoader;

    @Autowired
    private CassandraConfig cassandraConfig;

    @Autowired
    private PlayerStatsByGameRepository playerStatsByGameRepository;

    @Autowired
    private PlayerStatsByOpponentRepository playerStatsByOpponentRepository;

    @Autowired
    private StatsBySeasonRepository statsBySeasonRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_1));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_2));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_3));
    }

    @Test
    public void whenLoadingStatsWithBatch_thenAvailableOnRetrieval() throws Exception {
        Date gameDate = new GregorianCalendar(2014, Calendar.MARCH, 30).getTime();
        UUID gameId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        MadeAttemptedStat fieldGoals = new MadeAttemptedStat(12,16);
        MadeAttemptedStat threePoints = new MadeAttemptedStat(4,6);
        MadeAttemptedStat freeThrows = new MadeAttemptedStat(8,8);

        PlayerStatsByGame playerStatsByGame = new PlayerStatsByGame(gameId, gameDate, playerId, 2014, "Washington Wizards", "New York Knicks", 39, "John Wall", fieldGoals, threePoints, freeThrows, 3, 5, 8, 7, 1, 0, 1, 4, 29, 31);
        PlayerStatsByOpponent playerStatsByOpponent = new PlayerStatsByOpponent("New York Knicks", "John Wall", "Washington Wizards", 2014, gameDate, gameId, playerId, 39, fieldGoals, threePoints, freeThrows, 3, 5, 8, 7, 1, 0, 1, 4, 29, 31);
        StatsBySeason statsBySeason = new StatsBySeason(2014, Calendar.MARCH, "Washington Wizards", "John Wall", "New York Knicks", gameId, playerId, gameDate, 39, fieldGoals, threePoints, freeThrows, 3, 5, 8, 7, 1, 0, 1, 4, 29, 31);

        statsTablesLoader.insertStatsBatch(playerStatsByGame, playerStatsByOpponent, statsBySeason);

        List<PlayerStatsByGame> savedGameStats = playerStatsByGameRepository.getGameStats(gameId);
        List<PlayerStatsByOpponent> savedPlayerStatsByOpponent = playerStatsByOpponentRepository.getPlayerStatsByOpponent("New York Knicks", "John Wall");
        List<StatsBySeason> savedStatsBySeason = statsBySeasonRepository.getPlayerStatsByYearAndMonth(2014, Calendar.MARCH, "Washington Wizards", "John Wall");

        assertEquals(1, savedGameStats.size());
        assertEquals(1, savedPlayerStatsByOpponent.size());
        assertEquals(1, savedStatsBySeason.size());
        assertTrue(savedGameStats.contains(playerStatsByGame));
        assertTrue(savedPlayerStatsByOpponent.contains(playerStatsByOpponent));
        assertTrue(savedStatsBySeason.contains(statsBySeason));
    }
}
