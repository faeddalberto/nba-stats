package com.faeddalberto.nbastats.model.loader;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.domain.GameBySeasonTeams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GameTablesLoader {

    @Autowired
    private CassandraConfig cassandraConfig;

    public void insertGameBatch(Game game, GameBySeasonTeams gameBySeasonTeams) throws Exception {
        Batch batch = QueryBuilder.batch();

        batch.add(gameInsert(game));
        batch.add(gameBySeasonTeamsInsert(gameBySeasonTeams));

        cassandraConfig.cassandraTemplate().execute(batch);
    }

    private Insert gameInsert(Game game) {
        List<String> gameColNames = Arrays.asList("game_id", "season", "date", "season_type", "home_team", "home_team_score", "visitor_team", "visitor_team_score");
        List<Object> gameColValues = Arrays.asList(game.getGameId(), game.getSeason(), game.getDate(), game.getSeasonType(), game.getHomeTeam(), game.getHomeTeamScore(), game.getVisitorTeam(), game.getVisitorTeamScore());

        return QueryBuilder.insertInto("nba", "game").values(gameColNames, gameColValues);
    }

    private Insert gameBySeasonTeamsInsert(GameBySeasonTeams gameBySeasonTeams) {
        List<String> gameBySeasonTeamsColNames = Arrays.asList("season", "home_team", "visitor_team", "date",  "game_id", "home_team_score", "visitor_team_score","season_type");
        List<Object> gameBySeasonTeamsColValues = Arrays.asList(gameBySeasonTeams.getSeason(), gameBySeasonTeams.getHomeTeam(), gameBySeasonTeams.getVisitorTeam(), gameBySeasonTeams.getDate(), gameBySeasonTeams.getGameId(), gameBySeasonTeams.getHomeTeamScore(), gameBySeasonTeams.getVisitorTeamScore(), gameBySeasonTeams.getSeasonType());

        return QueryBuilder.insertInto("nba", "game_by_season_teams").values(gameBySeasonTeamsColNames, gameBySeasonTeamsColValues);
    }
}
