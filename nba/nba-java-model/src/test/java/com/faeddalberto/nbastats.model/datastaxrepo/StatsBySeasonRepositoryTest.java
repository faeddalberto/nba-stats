package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.StatsBySeason;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class StatsBySeasonRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "stats_by_season";

    @Autowired
    private StatsBySeasonRepository statsBySeasonRepository;

    @Autowired
    private CassandraConfig cassandraConfig;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingStats_thenAvailableOnRetrieval_V1() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(9, 12);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(6, 6);
        Date gameDate = new GregorianCalendar(2015, Calendar.NOVEMBER, 24).getTime();
        StatsBySeason statsBySeason = new StatsBySeason(2016, 11, "Houston Rockets", "Tim Duncan", "San Antonio Spurs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);

        mapper.save(statsBySeason);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        StatsBySeason savedStats = statsBySeasonList.get(0);
        assertTrue(statsBySeason.equals(savedStats));
    }

    @Test
    public void whenSavingStats_thenAvailableOnRetrieval_V2() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(9, 12);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(6, 6);
        Date gameDate = new GregorianCalendar(2015, Calendar.NOVEMBER, 24).getTime();
        StatsBySeason statsBySeason = new StatsBySeason(2016, 1, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);

        mapper.save(statsBySeason);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeasonAndMonth(2016, 1);

        StatsBySeason savedStats = statsBySeasonList.get(0);
        assertTrue(statsBySeason.equals(savedStats));
    }

    @Test
    public void whenSavingStats_thenAvailableOnRetrieval_V3() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        StatsBySeason beliStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", UUID.randomUUID(), UUID.randomUUID(), 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason cousinStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Demarcus Cousin", "New York Knicks", UUID.randomUUID(), UUID.randomUUID(), 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);

        mapper.save(beliStats);
        mapper.save(cousinStats);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getTeamStatsBySeasonAndMonth(2016, Calendar.MARCH, "Sacramento Kings");

        assertEquals(2, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats));
        assertTrue(statsBySeasonList.contains(cousinStats));
    }

    @Test
    public void whenSavingStats_thenAvailableOnRetrieval_V4() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        UUID gameId1 = UUID.randomUUID();
        UUID gameId2 = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        StatsBySeason beliStats1 = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", gameId1, playerId, 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason beliStats2 = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "New York Knicks", gameId2, playerId, 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);

        mapper.save(beliStats1);
        mapper.save(beliStats2);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getTeamStatsBySeasonAndMonth(2016, Calendar.MARCH, "Sacramento Kings");

        assertEquals(2, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats1));
        assertTrue(statsBySeasonList.contains(beliStats2));
    }

    @Test
    public void whenUpdatingStats_thenAvailableOnRetrieval() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(9, 12);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(6, 6);
        Date gameDate = new GregorianCalendar(2015, Calendar.NOVEMBER, 24).getTime();
        StatsBySeason statsBySeason = new StatsBySeason(2016, 1, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);

        mapper.save(statsBySeason);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeasonAndMonth(2016, 1);

        StatsBySeason savedStats = statsBySeasonList.get(0);
        assertTrue(statsBySeason.equals(savedStats));

        savedStats.setAssists(15);
        savedStats.setFreeThrows(new MadeAttemptedStat(8, 9));
        savedStats.setPoints(17);

        mapper.save(savedStats);

        List<StatsBySeason> updatedStatsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeasonAndMonth(2016, 1);

        StatsBySeason updatedStats = updatedStatsBySeasonList.get(0);
        assertTrue(updatedStats.equals(savedStats));
    }

    @Test
    public void whenDeletingStats_thenNotAvailableOnRetrieval_V1() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        Date gameDate3 = new GregorianCalendar(2016, Calendar.JANUARY, 24).getTime();
        StatsBySeason beliStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", UUID.randomUUID(), UUID.randomUUID(), 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason cousinStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Demarcus Cousin", "New York Knicks", UUID.randomUUID(), UUID.randomUUID(), 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);
        StatsBySeason ervinStats = new StatsBySeason(2016, Calendar.JANUARY, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate3, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);
        mapper.save(beliStats);
        mapper.save(cousinStats);
        mapper.save(ervinStats);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        assertEquals(3, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats));
        assertTrue(statsBySeasonList.contains(cousinStats));
        assertTrue(statsBySeasonList.contains(ervinStats));

        statsBySeasonRepository.deleteLeagueStatsBySeason(2016);

        List<StatsBySeason> deletedStats = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        assertEquals(0, deletedStats.size());
    }

    @Test
    public void whenDeletingStats_thenNotAvailableOnRetrieval_V2() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        Date gameDate3 = new GregorianCalendar(2016, Calendar.JANUARY, 24).getTime();
        StatsBySeason beliStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", UUID.randomUUID(), UUID.randomUUID(), 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason cousinStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Demarcus Cousin", "New York Knicks", UUID.randomUUID(), UUID.randomUUID(), 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);
        StatsBySeason ervinStats = new StatsBySeason(2016, Calendar.JANUARY, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate3, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);
        mapper.save(beliStats);
        mapper.save(cousinStats);
        mapper.save(ervinStats);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        assertEquals(3, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats));
        assertTrue(statsBySeasonList.contains(cousinStats));
        assertTrue(statsBySeasonList.contains(ervinStats));

        statsBySeasonRepository.deleteLeagueStatsBySeasonAndMonth(2016, Calendar.MARCH);

        List<StatsBySeason> deletedStats = statsBySeasonRepository.getLeagueStatsBySeasonAndMonth(2016, Calendar.MARCH);

        assertEquals(0, deletedStats.size());
    }

    @Test
    public void whenDeletingStats_thenNotAvailableOnRetrieval_V3() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        Date gameDate3 = new GregorianCalendar(2016, Calendar.JANUARY, 24).getTime();
        StatsBySeason beliStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", UUID.randomUUID(), UUID.randomUUID(), 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason cousinStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Demarcus Cousin", "New York Knicks", UUID.randomUUID(), UUID.randomUUID(), 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);
        StatsBySeason ervinStats = new StatsBySeason(2016, Calendar.JANUARY, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate3, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);
        mapper.save(beliStats);
        mapper.save(cousinStats);
        mapper.save(ervinStats);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        assertEquals(3, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats));
        assertTrue(statsBySeasonList.contains(cousinStats));
        assertTrue(statsBySeasonList.contains(ervinStats));

        statsBySeasonRepository.deleteTeamStatsBySeasonAndMonth(2016, Calendar.MARCH, "Sacramento Kings");

        List<StatsBySeason> deletedStats = statsBySeasonRepository.getTeamStatsBySeasonAndMonth(2016, Calendar.MARCH, "Sacramento Kings");

        assertEquals(0, deletedStats.size());
    }

    @Test
    public void whenDeletingStats_thenNotAvailableOnRetrieval_V4() throws Exception {
        Mapper<StatsBySeason> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(StatsBySeason.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(1, 2);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(3, 4);
        Date gameDate1 = new GregorianCalendar(2016, Calendar.MARCH, 8).getTime();
        Date gameDate2 = new GregorianCalendar(2016, Calendar.MARCH, 12).getTime();
        Date gameDate3 = new GregorianCalendar(2016, Calendar.JANUARY, 24).getTime();
        StatsBySeason beliStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli", "Golden State Warriors", UUID.randomUUID(), UUID.randomUUID(), 4, 4, gameDate1, 3, fg_ma, ft_ma, 21, 3, 2, 15, 15, 1, 6, 2, threePts_ma);
        StatsBySeason cousinStats = new StatsBySeason(2016, Calendar.MARCH, "Sacramento Kings", "Demarcus Cousin", "New York Knicks", UUID.randomUUID(), UUID.randomUUID(), 1, 2, gameDate2, 1, fg_ma, ft_ma, 32, 7, 4, 11, 12, 5, 8, 6, threePts_ma);
        StatsBySeason ervinStats = new StatsBySeason(2016, Calendar.JANUARY, "Toronto Raptors", "Kyrie Ervin", "Cleveland Cavs", UUID.randomUUID(), UUID.randomUUID(), 2, 4, gameDate3, 3, fg_ma, ft_ma, 25, 8, 3, 23, 15, 1, 11, 2, threePts_ma);
        mapper.save(beliStats);
        mapper.save(cousinStats);
        mapper.save(ervinStats);

        List<StatsBySeason> statsBySeasonList = statsBySeasonRepository.getLeagueStatsBySeason(2016);

        assertEquals(3, statsBySeasonList.size());
        assertTrue(statsBySeasonList.contains(beliStats));
        assertTrue(statsBySeasonList.contains(cousinStats));
        assertTrue(statsBySeasonList.contains(ervinStats));

        statsBySeasonRepository.deletePlayerStatsBySeasonAndMonth(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli");

        List<StatsBySeason> deletedStats = statsBySeasonRepository.getPlayerStatsByYearAndMonth(2016, Calendar.MARCH, "Sacramento Kings", "Marco Belinelli");

        assertEquals(0, deletedStats.size());
    }
}
