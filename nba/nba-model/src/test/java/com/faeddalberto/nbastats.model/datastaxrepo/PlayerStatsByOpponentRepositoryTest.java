package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByOpponent;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PlayerStatsByOpponentRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "player_stats_by_opponent";

    @Autowired
    private PlayerStatsByOpponentRepository playerStatsByOpponentRepository;

    @Autowired
    private CassandraConfig cassandraConfig;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingPlayerStats_thenAvailableOnRetrieval_V1() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(9, 10);
        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        PlayerStatsByOpponent playerStatsByOpponent = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), UUID.randomUUID(), 39, fg_ma, threePts_ma, ft_ma, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        mapper.save(playerStatsByOpponent);

        List<PlayerStatsByOpponent> playerStatsByOpponents = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        PlayerStatsByOpponent savedPlayerStats = playerStatsByOpponents.get(0);
        assertTrue(playerStatsByOpponent.equals(savedPlayerStats));
    }

    @Test
    public void whenSavingPlayerStats_thenAvailableOnRetrieval_V2() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(9, 10);
        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        PlayerStatsByOpponent playerStatsByOpponent = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), UUID.randomUUID(), 39, fg_ma, threePts_ma, ft_ma, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        mapper.save(playerStatsByOpponent);

        List<PlayerStatsByOpponent> playerStatsByOpponents = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Paul Pierce", "Boston Celtics");

        PlayerStatsByOpponent savedPlayerStats = playerStatsByOpponents.get(0);
        assertTrue(playerStatsByOpponent.equals(savedPlayerStats));
    }

    @Test
    public void whenSavingPlayerStats_thenAvailableOnRetrieval_V3() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(9, 10);
        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        PlayerStatsByOpponent playerStatsByOpponent = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), UUID.randomUUID(), 39, fg_ma, threePts_ma, ft_ma, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        mapper.save(playerStatsByOpponent);

        List<PlayerStatsByOpponent> playerStatsByOpponents = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeamAndSeason("Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011);

        PlayerStatsByOpponent savedPlayerStats = playerStatsByOpponents.get(0);
        assertTrue(playerStatsByOpponent.equals(savedPlayerStats));
    }

    @Test
    public void whenUpdatingPlayerStats_thenAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(9, 10);
        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        PlayerStatsByOpponent playerStatsByOpponent = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), UUID.randomUUID(), 39, fg_ma, threePts_ma, ft_ma, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        mapper.save(playerStatsByOpponent);

        List<PlayerStatsByOpponent> playerStatsByOpponents = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeamAndSeason("Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011);

        PlayerStatsByOpponent savedPlayerStats = playerStatsByOpponents.get(0);
        assertTrue(playerStatsByOpponent.equals(savedPlayerStats));

        savedPlayerStats.setAssists(9);
        savedPlayerStats.setDefensiveRebounds(7);
        savedPlayerStats.setMinsPlayed(42);

        mapper.save(savedPlayerStats);

        List<PlayerStatsByOpponent> updatedStatsByOpponent = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeamAndSeason("Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011);

        PlayerStatsByOpponent updatedPlayerStats = updatedStatsByOpponent.get(0);
        assertTrue(savedPlayerStats.equals(updatedPlayerStats));
    }

    @Test
    public void whenSavingMultiplePlayersStats_thenAllAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        Date gameDate_2 = new GregorianCalendar(2012, 2, 11).getTime();

        MadeAttemptedStat pierce_fg_ma_1 = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat pierce_threePts_ma_1 = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat pierce_ft_ma_1 = new MadeAttemptedStat(9, 10);
        UUID pierceId = UUID.randomUUID();
        PlayerStatsByOpponent pierceStats_1 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2012, gameDate, UUID.randomUUID(), pierceId, 39, pierce_fg_ma_1, pierce_threePts_ma_1, pierce_ft_ma_1, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        MadeAttemptedStat pierce_fg_ma_2 = new MadeAttemptedStat(18, 24);
        MadeAttemptedStat pierce_threePts_ma_2 = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat pierce_ft_ma_2 = new MadeAttemptedStat(4, 6);
        PlayerStatsByOpponent pierceStats_2 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2012, gameDate_2, UUID.randomUUID(), pierceId, 43, pierce_fg_ma_2, pierce_threePts_ma_2, pierce_ft_ma_2, 5, 4, 9, 2, 2, 2, 1, 3, 23, 31);


        MadeAttemptedStat rondo_fg_ma = new MadeAttemptedStat(10, 15);
        MadeAttemptedStat rondo_threePts_ma = new MadeAttemptedStat(3, 4);
        MadeAttemptedStat rondo_ft_ma = new MadeAttemptedStat(11, 11);
        PlayerStatsByOpponent rondoStats = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Rajon Rondo", "Boston Celtics", 2012, gameDate, UUID.randomUUID(), UUID.randomUUID(), 42, rondo_fg_ma, rondo_threePts_ma, rondo_ft_ma, 2, 4, 7, 11, 1, 0, 3, 2, 19, 21);

        mapper.save(pierceStats_1);
        mapper.save(pierceStats_2);
        mapper.save(rondoStats);

        List<PlayerStatsByOpponent> savedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Paul Pierce", "Boston Celtics");
        List<PlayerStatsByOpponent> savedRondoStats = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Rajon Rondo", "Boston Celtics");

        assertTrue(savedPierceStats.contains(pierceStats_1));
        assertTrue(savedPierceStats.contains(pierceStats_2));
        assertTrue(savedRondoStats.contains(rondoStats));
    }

    @Test
    public void whenDeletingPlayersStats_thenNotAvailableOnRetrieval_V1() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        Date gameDate_2 = new GregorianCalendar(2012, 2, 11).getTime();

        MadeAttemptedStat pierce_fg_ma_1 = new MadeAttemptedStat(14, 21);
        MadeAttemptedStat pierce_threePts_ma_1 = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat pierce_ft_ma_1 = new MadeAttemptedStat(9, 10);
        UUID pierceId = UUID.randomUUID();
        PlayerStatsByOpponent pierceStats_1 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2012, gameDate, UUID.randomUUID(), pierceId, 39, pierce_fg_ma_1, pierce_threePts_ma_1, pierce_ft_ma_1, 7, 3, 10, 6, 1, 0, 2, 4, 21, 33);

        MadeAttemptedStat pierce_fg_ma_2 = new MadeAttemptedStat(18, 24);
        MadeAttemptedStat pierce_threePts_ma_2 = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat pierce_ft_ma_2 = new MadeAttemptedStat(4, 6);
        PlayerStatsByOpponent pierceStats_2 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2012, gameDate_2, UUID.randomUUID(), pierceId, 43, pierce_fg_ma_2, pierce_threePts_ma_2, pierce_ft_ma_2, 5, 4, 9, 2, 2, 2, 1, 3, 23, 31);

        mapper.save(pierceStats_1);
        mapper.save(pierceStats_2);

        List<PlayerStatsByOpponent> savedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Paul Pierce", "Boston Celtics");

        assertTrue(savedPierceStats.contains(pierceStats_1));
        assertTrue(savedPierceStats.contains(pierceStats_2));

        playerStatsByOpponentRepository.deletePlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        List<PlayerStatsByOpponent> deletedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Paul Pierce", "Boston Celtics");

        assertFalse(deletedPierceStats.contains(pierceStats_1));
        assertFalse(deletedPierceStats.contains(pierceStats_2));
    }

    @Test
    public void whenDeletingPlayersStats_thenNotAvailableOnRetrieval_V2() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        Date gameDate_2 = new GregorianCalendar(2016, 2, 23).getTime();

        UUID pierceId = UUID.randomUUID();

        MadeAttemptedStat pierce_fg_ma = new MadeAttemptedStat(18, 24);
        MadeAttemptedStat pierce_threePts_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat pierce_ft_ma = new MadeAttemptedStat(4, 6);
        PlayerStatsByOpponent pierceStats = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), pierceId, 43, pierce_fg_ma, pierce_threePts_ma, pierce_ft_ma, 5, 4, 9, 2, 2, 2, 1, 3, 23, 31);

        MadeAttemptedStat pierce_fg_ma_1 = new MadeAttemptedStat(5, 8);
        MadeAttemptedStat pierce_threePts_ma_1 = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat pierce_ft_ma_1 = new MadeAttemptedStat(2, 2);
        PlayerStatsByOpponent pierceStats_1 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Los Angeles Clippers", 2016, gameDate_2, UUID.randomUUID(), pierceId, 21, pierce_fg_ma_1, pierce_threePts_ma_1, pierce_ft_ma_1, 2, 3, 5, 2, 0, 0, 4, 2, 11, 13);

        mapper.save(pierceStats);
        mapper.save(pierceStats_1);

        List<PlayerStatsByOpponent> savedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        assertTrue(savedPierceStats.contains(pierceStats));
        assertTrue(savedPierceStats.contains(pierceStats_1));

        playerStatsByOpponentRepository.deletePlayerStatsByOpponentAndPlayerTeam("Los Angeles Lakers", "Paul Pierce", "Boston Celtics");

        List<PlayerStatsByOpponent> deletedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        assertFalse(deletedPierceStats.contains(pierceStats));
        assertTrue(deletedPierceStats.contains(pierceStats_1));
    }

    @Test
    public void whenDeletingPlayersStats_thenNotAvailableOnRetrieval_V3() throws Exception {
        Mapper<PlayerStatsByOpponent> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByOpponent.class);

        Date gameDate = new GregorianCalendar(2011, 12, 24).getTime();
        Date gameDate_2 = new GregorianCalendar(2016, 2, 23).getTime();

        UUID pierceId = UUID.randomUUID();

        MadeAttemptedStat pierce_fg_ma = new MadeAttemptedStat(18, 24);
        MadeAttemptedStat pierce_threePts_ma = new MadeAttemptedStat(3, 5);
        MadeAttemptedStat pierce_ft_ma = new MadeAttemptedStat(4, 6);
        PlayerStatsByOpponent pierceStats = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Boston Celtics", 2011, gameDate, UUID.randomUUID(), pierceId, 43, pierce_fg_ma, pierce_threePts_ma, pierce_ft_ma, 5, 4, 9, 2, 2, 2, 1, 3, 23, 31);

        MadeAttemptedStat pierce_fg_ma_1 = new MadeAttemptedStat(5, 8);
        MadeAttemptedStat pierce_threePts_ma_1 = new MadeAttemptedStat(4, 7);
        MadeAttemptedStat pierce_ft_ma_1 = new MadeAttemptedStat(2, 2);
        PlayerStatsByOpponent pierceStats_1 = new PlayerStatsByOpponent(
                "Los Angeles Lakers", "Paul Pierce", "Los Angeles Clippers", 2016, gameDate_2, UUID.randomUUID(), pierceId, 21, pierce_fg_ma_1, pierce_threePts_ma_1, pierce_ft_ma_1, 2, 3, 5, 2, 0, 0, 4, 2, 11, 13);

        mapper.save(pierceStats);
        mapper.save(pierceStats_1);

        List<PlayerStatsByOpponent> savedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        assertTrue(savedPierceStats.contains(pierceStats));
        assertTrue(savedPierceStats.contains(pierceStats_1));

        playerStatsByOpponentRepository.deletePlayerStatsByOpponentAndPlayerTeamAndSeason("Los Angeles Lakers", "Paul Pierce", "Los Angeles Clippers", 2016);

        List<PlayerStatsByOpponent> deletedPierceStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Los Angeles Lakers", "Paul Pierce");

        assertTrue(deletedPierceStats.contains(pierceStats));
        assertFalse(deletedPierceStats.contains(pierceStats_1));
    }
}
