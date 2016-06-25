package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.MadeAttemptedStat;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class PlayerStatsByGameRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "player_stats_by_game";

    @Autowired
    private PlayerStatsByGameRepository playerStatsByGameRepository;

    @Autowired
    private CassandraConfig cassandraConfig;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingPlayerStats_thenAvailableOnRetrieval_V1() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();
        PlayerStatsByGame playerStatsByGame = new PlayerStatsByGame(UUID.randomUUID(), gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", fg_ma, threePts_ma, ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        mapper.save(playerStatsByGame);

        List<PlayerStatsByGame> savedPlayerStats = playerStatsByGameRepository.getAllGamesStats();

        assertTrue(playerStatsByGame.equals(savedPlayerStats.get(0)));
    }

    @Test
    public void whenSavingPlayerStats_thenAvailableOnRetrieval_V2() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();
        PlayerStatsByGame playerStatsByGame = new PlayerStatsByGame(UUID.randomUUID(), gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", fg_ma, threePts_ma, ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        mapper.save(playerStatsByGame);

        List<PlayerStatsByGame> savedPlayerStats = playerStatsByGameRepository.getGameStats(playerStatsByGame.getGameId());

        assertTrue(playerStatsByGame.equals(savedPlayerStats.get(0)));
    }

    @Test
    public void whenUpdatingPlayerStats_thenAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();
        PlayerStatsByGame playerStatsByGame = new PlayerStatsByGame(UUID.randomUUID(), gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", fg_ma, threePts_ma, ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        mapper.save(playerStatsByGame);

        List<PlayerStatsByGame> savedPlayerStats = playerStatsByGameRepository.getGameStats(playerStatsByGame.getGameId());

        assertTrue(playerStatsByGame.equals(savedPlayerStats.get(0)));

        playerStatsByGame.setAssists(7);
        playerStatsByGame.setPoints(19);
        playerStatsByGame.setDefensiveRebounds(9);
        playerStatsByGame.setOffensiveRebounds(13);

        mapper.save(playerStatsByGame);

        List<PlayerStatsByGame> updatedPlayerStats = playerStatsByGameRepository.getGameStats(playerStatsByGame.getGameId());

        assertTrue(playerStatsByGame.equals(updatedPlayerStats.get(0)));
    }

    @Test
    public void whenSavingMultiplePlayersStats_thenAllAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat noah_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat noah_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat noah_ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();

        UUID gameId = UUID.randomUUID();

        PlayerStatsByGame noahStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", noah_fg_ma, noah_threePts_ma, noah_ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        MadeAttemptedStat butler_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat butler_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat butler_ft_ma = new MadeAttemptedStat(5, 10);
        PlayerStatsByGame butlerStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "POINT GUARD", "Orlando Magic", 39, "Jimmy Butler", butler_fg_ma, butler_threePts_ma, butler_ft_ma, 7, 2, 9, 8, 5, 1, 1, 2, 36, 29);

        mapper.save(noahStatsByGame);
        mapper.save(butlerStatsByGame);

        PlayerStatsByGame savedNoahStats = playerStatsByGameRepository.getPlayerGameStats(noahStatsByGame.getGameId(), noahStatsByGame.getPlayerId());
        PlayerStatsByGame savedButlerStats = playerStatsByGameRepository.getPlayerGameStats(butlerStatsByGame.getGameId(), butlerStatsByGame.getPlayerId());

        assertTrue(noahStatsByGame.equals(savedNoahStats));
        assertTrue(butlerStatsByGame.equals(savedButlerStats));
    }

    @Test
    public void whenDeletingGameStats_thenNotAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat noah_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat noah_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat noah_ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();

        UUID gameId = UUID.randomUUID();

        PlayerStatsByGame noahStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", noah_fg_ma, noah_threePts_ma, noah_ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        MadeAttemptedStat butler_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat butler_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat butler_ft_ma = new MadeAttemptedStat(5, 10);
        PlayerStatsByGame butlerStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "POINT GUARD", "Orlando Magic", 39, "Jimmy Butler", butler_fg_ma, butler_threePts_ma, butler_ft_ma, 7, 2, 9, 8, 5, 1, 1, 2, 36, 29);

        mapper.save(noahStatsByGame);
        mapper.save(butlerStatsByGame);


        PlayerStatsByGame savedNoahStats = playerStatsByGameRepository.getPlayerGameStats(noahStatsByGame.getGameId(), noahStatsByGame.getPlayerId());
        PlayerStatsByGame savedButlerStats = playerStatsByGameRepository.getPlayerGameStats(butlerStatsByGame.getGameId(), butlerStatsByGame.getPlayerId());

        assertTrue(noahStatsByGame.equals(savedNoahStats));
        assertTrue(butlerStatsByGame.equals(savedButlerStats));

        playerStatsByGameRepository.deleteGameStats(gameId);

        assertTrue(playerStatsByGameRepository.getGameStats(gameId).isEmpty());
    }

    @Test
    public void whenDeletingPlayerGameStats_thenNotAvailableOnRetrieval() throws Exception {
        Mapper<PlayerStatsByGame> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerStatsByGame.class);

        MadeAttemptedStat noah_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat noah_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat noah_ft_ma = new MadeAttemptedStat(5, 10);
        Date gameDate = new GregorianCalendar(2015, 11, 21).getTime();

        UUID gameId = UUID.randomUUID();

        PlayerStatsByGame noahStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "CENTER", "Orlando Magic", 16, "Joakim Noah", noah_fg_ma, noah_threePts_ma, noah_ft_ma, 7, 4, 11, 4, 3, 4, 3, 4, 21, 13);

        MadeAttemptedStat butler_fg_ma = new MadeAttemptedStat(4, 6);
        MadeAttemptedStat butler_threePts_ma = new MadeAttemptedStat(0, 0);
        MadeAttemptedStat butler_ft_ma = new MadeAttemptedStat(5, 10);
        PlayerStatsByGame butlerStatsByGame = new PlayerStatsByGame(gameId, gameDate, UUID.randomUUID(), 2016,
                "Chicago Bulls", "POINT GUARD", "Orlando Magic", 39, "Jimmy Butler", butler_fg_ma, butler_threePts_ma, butler_ft_ma, 7, 2, 9, 8, 5, 1, 1, 2, 36, 29);

        mapper.save(noahStatsByGame);
        mapper.save(butlerStatsByGame);

        PlayerStatsByGame savedNoahStats = playerStatsByGameRepository.getPlayerGameStats(noahStatsByGame.getGameId(), noahStatsByGame.getPlayerId());
        PlayerStatsByGame savedButlerStats = playerStatsByGameRepository.getPlayerGameStats(butlerStatsByGame.getGameId(), butlerStatsByGame.getPlayerId());

        assertTrue(noahStatsByGame.equals(savedNoahStats));
        assertTrue(butlerStatsByGame.equals(savedButlerStats));

        playerStatsByGameRepository.deletePlayerGameStats(gameId, noahStatsByGame.getPlayerId());

        assertTrue(playerStatsByGameRepository.getPlayerGameStats(gameId, noahStatsByGame.getPlayerId()) == null);
    }

}
