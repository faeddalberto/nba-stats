package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameStatsFileReaderTest {

    @Test
    public void whenReadingGamesStatsFile_thenGameStatsMapIsReturned() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        Map<String, Player> players = getPlayers(classLoader);
        Map<String, Team> teams = getTeams(classLoader);
        Map<String, Game> games = getGames(classLoader);
        GamesStatsFileReader statsFileReader = new GamesStatsFileReader(players, teams, games);
        Map<String, List<PlayerStatsByGame>> gameStatsMap =
                statsFileReader.readGamesStatsFiles(classLoader.getResource("2015-2016_season/games-stats").getPath());

        assertTrue(gameStatsMap.containsKey("400827888"));
        assertEquals(26, gameStatsMap.get("400827888").size());
        List<PlayerStatsByGame> playersStats = gameStatsMap.get("400827888");

        PlayerStatsByGame ilyasovaStats = playersStats.stream()
                .filter(playerStats -> playerStats.getPlayerName().contains("Ilyasova"))
                .findFirst()
                .get();

        Game game = games.get("400827888");
        Player ilyasova = players.get("2767");
        assertEquals(ilyasova.getPlayerId(), ilyasovaStats.getPlayerId());
        assertEquals(ilyasovaStats.getGameId(), game.getGameId());
        assertEquals(2016, ilyasovaStats.getSeason());
        assertEquals("Detroit Pistons", ilyasovaStats.getPlayerTeam());
        assertEquals(34, ilyasovaStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(6,12), ilyasovaStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(3,6), ilyasovaStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(1,2), ilyasovaStats.getFreeThrows());
        assertEquals(3, ilyasovaStats.getOffensiveRebounds());
        assertEquals(4, ilyasovaStats.getDefensiveRebounds());
        assertEquals(7, ilyasovaStats.getTotalRebounds());
        assertEquals(3, ilyasovaStats.getAssists());
        assertEquals(0, ilyasovaStats.getSteals());
        assertEquals(1, ilyasovaStats.getBlocks());
        assertEquals(3, ilyasovaStats.getTurnovers());
        assertEquals(4, ilyasovaStats.getPersonalFauls());
        assertEquals(20, ilyasovaStats.getPlusMinus());
        assertEquals(16, ilyasovaStats.getPoints());


        PlayerStatsByGame korverStats = playersStats.stream()
                .filter(playerStats -> playerStats.getPlayerName().contains("Korver"))
                .findFirst()
                .get();

        Player korver = players.get("2011");
        assertEquals(korverStats.getPlayerId(), korver.getPlayerId());
        assertEquals(korverStats.getGameId(), game.getGameId());
        assertEquals(2016, korverStats.getSeason());
        assertEquals("Atlanta Hawks", korverStats.getPlayerTeam());
        assertEquals(29, korverStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(3,9), korverStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(1,5), korverStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), korverStats.getFreeThrows());
        assertEquals(0, korverStats.getOffensiveRebounds());
        assertEquals(2, korverStats.getDefensiveRebounds());
        assertEquals(2, korverStats.getTotalRebounds());
        assertEquals(1, korverStats.getAssists());
        assertEquals(1, korverStats.getSteals());
        assertEquals(0, korverStats.getBlocks());
        assertEquals(1, korverStats.getTurnovers());
        assertEquals(4, korverStats.getPersonalFauls());
        assertEquals(-9, korverStats.getPlusMinus());
        assertEquals(7, korverStats.getPoints());
    }

    Map<String, Player> getPlayers(ClassLoader classLoader) throws IOException {
        PlayersFileReader playersFileReader = new PlayersFileReader();
        return playersFileReader.readPlayerFiles(classLoader.getResource("players").getPath());
    }

    Map<String, Team> getTeams(ClassLoader classLoader) throws FileNotFoundException {
        TeamsFileReader teamsFileReader = new TeamsFileReader();
        return teamsFileReader.readTeamsFile(classLoader.getResource("teams_conf_div.csv").getPath());
    }

    Map<String, Game> getGames(ClassLoader classLoader) throws IOException {
        GamesFileReader gamesFileReader = new GamesFileReader(getTeams(classLoader));
        return gamesFileReader.readGamesFiles(classLoader.getResource("2015-2016_season/games").getPath());
    }
}