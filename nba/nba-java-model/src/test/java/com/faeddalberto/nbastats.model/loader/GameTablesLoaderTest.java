package com.faeddalberto.nbastats.model.loader;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.domain.GameBySeasonTeams;
import com.faeddalberto.nbastats.model.springrepo.GameBySeasonTeamsRepository;
import com.faeddalberto.nbastats.model.springrepo.GameRepository;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static com.faeddalberto.nbastats.model.enums.SeasonType.REGULAR_SEASON;
import static org.junit.Assert.assertEquals;

public class GameTablesLoaderTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME_1 = "game";
    public static final String DATA_TABLE_NAME_2 = "game_by_season_teams";

    @Autowired
    private GameTablesLoader gameTablesLoader;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameBySeasonTeamsRepository gameBySeasonTeamsRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_1));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_2));
    }

    @Test
    public void whenLoadingGameWithBatch_thenAvailableOnRetrieval() throws Exception {
        Date gameDate = new GregorianCalendar(2015, 12, 11).getTime();
        UUID gameId = UUID.randomUUID();
        Game game = new Game(gameId, 2016, gameDate, REGULAR_SEASON.name(), "Houston Rockets", 101, "Los Angeles Lakers", 91);
        GameBySeasonTeams gameBySeasonTeams = new GameBySeasonTeams(2016, "Houston Rockets", "Los Angeles Lakers", gameDate, gameId, 101, 91, REGULAR_SEASON.name());

        gameTablesLoader.insertGameBatch(game, gameBySeasonTeams);

        Game insertedGame = gameRepository.findById(gameId);
        GameBySeasonTeams insertedGameBySeasonTeams = gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeam(2016, "Houston Rockets", "Los Angeles Lakers").get(0);

        assertEquals(game, insertedGame);
        assertEquals(gameBySeasonTeams, insertedGameBySeasonTeams);
    }
}
