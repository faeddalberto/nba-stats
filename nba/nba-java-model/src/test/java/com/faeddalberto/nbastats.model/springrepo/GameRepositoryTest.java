package com.faeddalberto.nbastats.model.springrepo;

import com.datastax.driver.core.utils.UUIDs;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.enums.SeasonType;
import org.cassandraunit.shaded.com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "game";

    @Autowired
    private GameRepository gameRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingGame_thenAvailableOnRetrieval() {
        final Game javaGame =
                new Game(UUIDs.random(), 2015, new Date(), SeasonType.REGULAR_SEASON.name(), "Toronto Raptors", 86, "Golden State Warriors", 91);

        gameRepository.save(ImmutableSet.of(javaGame));

        final Game game = gameRepository.findById(javaGame.getGameId());
        assertEquals(javaGame.getGameId(), game.getGameId());
    }

    @Test
    public void whenUpdatingGame_thenAvailableOnRetrieval() {
        final Game javaGame =
                new Game(UUIDs.random(), 2015, new Date(), SeasonType.REGULAR_SEASON.name(), "Cleveland Cavaliers", 91, "Miami Heat", 90);

        gameRepository.save(ImmutableSet.of(javaGame));
        final Game game = gameRepository.findById(javaGame.getGameId());
        assertEquals(javaGame.getGameId(), game.getGameId());

        javaGame.setSeason(2014);
        javaGame.setSeasonType(SeasonType.PLAYOFFS.name());
        gameRepository.save(javaGame);
        final Game updatedGame = gameRepository.findById(javaGame.getGameId());

        assertEquals(javaGame.getSeason(), updatedGame.getSeason());
        assertEquals(javaGame.getSeasonType(), updatedGame.getSeasonType());
    }

    @Test(expected = NullPointerException.class)
    public void whenDeletingGame_thenNotAvailableOnRetrieval() {
        final Game javaGame =
                new Game(UUIDs.random(), 2015, new Date(), SeasonType.REGULAR_SEASON.name(), "Toronto Raptors", 86, "Golden State Warriors", 91);

        gameRepository.save(ImmutableSet.of(javaGame));
        gameRepository.delete(javaGame);

        final Game game = gameRepository.findById(javaGame.getGameId());
        assertNotEquals(javaGame.getGameId(), game.getGameId());
    }

    @Test
    public void whenSavingMultipleGame_thenAllAvailableOnRetrieval() {
        final Game gameRaptorsVsWarriors =
                new Game(UUIDs.random(), 2015, new Date(), SeasonType.REGULAR_SEASON.name(), "Toronto Raptors", 86, "Golden State Warriors", 91);
        final Game gameKingsVsLaClippers =
                new Game(UUIDs.random(), 2015, new Date(), SeasonType.REGULAR_SEASON.name(), "Sacramento Kings", 75, "Los Angeles Clippers", 111);

        gameRepository.save(ImmutableSet.of(gameRaptorsVsWarriors));
        gameRepository.save(ImmutableSet.of(gameKingsVsLaClippers));

        final Iterable<Game> games = gameRepository.findAll();
        int gameCount = 0;
        for (final Game game : games) {
            gameCount++;
        }
        assertEquals(gameCount, 2);
    }
}
