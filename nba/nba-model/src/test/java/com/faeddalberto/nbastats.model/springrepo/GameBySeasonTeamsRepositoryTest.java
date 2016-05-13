package com.faeddalberto.nbastats.model.springrepo;

import com.datastax.driver.core.utils.UUIDs;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.domain.GameBySeasonTeams;
import com.faeddalberto.nbastats.model.enums.SeasonType;
import org.cassandraunit.shaded.com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameBySeasonTeamsRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "game_by_season_teams";

    @Autowired
    private GameBySeasonTeamsRepository gameBySeasonTeamsRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingGameBySeasonTeams_thenAvailableOnRetrieval_V1() {
        final GameBySeasonTeams javaGameBySeasonTeams =
                new GameBySeasonTeams(2016, "Chicago Bulls", "Indiana Pacers", new Date(), UUIDs.random(), 97, 95, SeasonType.REGULAR_SEASON.name());

        gameBySeasonTeamsRepository.save(ImmutableSet.of(javaGameBySeasonTeams));
        final Iterable<GameBySeasonTeams> games = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeam(2016, "Chicago Bulls", "Indiana Pacers");

        assertEquals(javaGameBySeasonTeams.getGameId(), games.iterator().next().getGameId());
    }

    @Test
    public void whenSavingGameBySeasonTeams_thenAvailableOnRetrieval_v2() {
        Date myDate = new GregorianCalendar(2014, 2, 11).getTime();

        final GameBySeasonTeams javaGameBySeasonTeams =
                new GameBySeasonTeams(2014, "Los Angeles Lakers", "Boston Celtics", myDate, UUIDs.timeBased(), 104, 111, SeasonType.REGULAR_SEASON.name());
        gameBySeasonTeamsRepository.save(ImmutableSet.of(javaGameBySeasonTeams));
        final Iterable<GameBySeasonTeams> games = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2014, "Los Angeles Lakers", "Boston Celtics", myDate);

        assertEquals(javaGameBySeasonTeams.getGameId(), games.iterator().next().getGameId());
    }

    @Test
    public void whenUpdatingGameBySeasonTeams_thenAvailableOnRetrieval() {
        final GameBySeasonTeams javaGameBySeasonTeams =
                new GameBySeasonTeams(2010, "Detroit Pistons", "New York Knicks", new Date(), UUIDs.random(), 81, 102, SeasonType.REGULAR_SEASON.name());

        gameBySeasonTeamsRepository.save(ImmutableSet.of(javaGameBySeasonTeams));
        final Iterable<GameBySeasonTeams> game = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeam(2010, "Detroit Pistons", "New York Knicks");
        assertEquals(javaGameBySeasonTeams.getGameId(), game.iterator().next().getGameId());

        javaGameBySeasonTeams.setHomeTeamScore(101);
        javaGameBySeasonTeams.setVisitorTeamScore(110);
        gameBySeasonTeamsRepository.save(ImmutableSet.of(javaGameBySeasonTeams));

        final Iterable<GameBySeasonTeams> updatedGame = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeam(2010, "Detroit Pistons", "New York Knicks");
        assertEquals(javaGameBySeasonTeams.getHomeTeamScore(), updatedGame.iterator().next().getHomeTeamScore());
        assertEquals(javaGameBySeasonTeams.getVisitorTeamScore(), updatedGame.iterator().next().getVisitorTeamScore());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDeletingGameBySeasonTeams_thenNotAvailableOnRetrieval() {
        Date myDate = new GregorianCalendar(2014, 2, 11).getTime();

        final GameBySeasonTeams javaGameBySeasonTeams =
                new GameBySeasonTeams(2014, "Los Angeles Lakers", "Boston Celtics", myDate, UUIDs.timeBased(), 104, 111, SeasonType.REGULAR_SEASON.name());
        gameBySeasonTeamsRepository.save(ImmutableSet.of(javaGameBySeasonTeams));
        gameBySeasonTeamsRepository.delete(javaGameBySeasonTeams);
        final Iterable<GameBySeasonTeams> games = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2014, "Los Angeles Lakers", "Boston Celtics", myDate);

        assertNotEquals(javaGameBySeasonTeams.getGameId(), games.iterator().next().getGameId());
    }

    @Test
    public void whenSavingMultipleGameBySeasonTeams_thenAllAvailableOnRetrieval() {
        final GameBySeasonTeams gameBullsVsPacers =
                new GameBySeasonTeams(2016, "Chicago Bulls", "Indiana Pacers", new Date(), UUIDs.random(), 97, 95, SeasonType.REGULAR_SEASON.name());

        final GameBySeasonTeams gameLaLakersVsMavs =
                new GameBySeasonTeams(2016, "Los Angeles Lakers", "Dallas Mavericks", new Date(), UUIDs.random(), 121, 122, SeasonType.REGULAR_SEASON.name());

        gameBySeasonTeamsRepository.save(ImmutableSet.of(gameBullsVsPacers));
        gameBySeasonTeamsRepository.save(ImmutableSet.of(gameLaLakersVsMavs));

        final Iterable<GameBySeasonTeams> games = gameBySeasonTeamsRepository.findAll();
        int gamesCount = 0;
        for (final GameBySeasonTeams game : games) {
            gamesCount++;
        }
        assertEquals(gamesCount, 2);
    }
}
