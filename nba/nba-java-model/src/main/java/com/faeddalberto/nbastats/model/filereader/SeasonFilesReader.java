package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.domain.Player;
import com.faeddalberto.nbastats.model.domain.PlayerStatsByGame;
import com.faeddalberto.nbastats.model.domain.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SeasonFilesReader {

    private static final String TEAMS_FILE_NAME = "teams_conf_div.csv";
    private static final String PLAYERS_FOLDER_NAME = "players";
    private static final String SEASON_FOLDER_NAME_PROLOG = "_season";
    private static final String GAMES_FOLDER_NAME = "games";
    private static final String GAMES_STATS_FOLDER_NAME = "games-stats";

    public Map<String, Game> readSeasonGames(String rootPath, String season, Map<String, Team> teams) throws IOException {
        return new GamesFileReader(teams).
                readGamesFiles(rootPath + "/" + season + SEASON_FOLDER_NAME_PROLOG + "/" + GAMES_FOLDER_NAME);
    }

    public Map<String, List<PlayerStatsByGame>> readSeasonGamesStats(
            String rootPath, String season, Map<String, Player> players, Map<String, Team> teams, Map<String, Game> games) throws IOException {
        return new GamesStatsFileReader(players, teams, games).
                readGamesStatsFiles(rootPath + "/" + season + SEASON_FOLDER_NAME_PROLOG + "/" + GAMES_STATS_FOLDER_NAME);
    }


    public Map<String, Team> readTeams(String rootPath) throws FileNotFoundException {
        return new TeamsFileReader().
                readTeamsFile(rootPath + "/" + TEAMS_FILE_NAME);
    }

    public Map<String, Player> readPlayers(String rootPath) throws IOException {
        return new PlayersFileReader().
                readPlayerFiles(rootPath + "/" + PLAYERS_FOLDER_NAME);
    }
}
