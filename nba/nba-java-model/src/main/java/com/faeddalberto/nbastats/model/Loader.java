package com.faeddalberto.nbastats.model;

import com.faeddalberto.nbastats.model.domain.*;
import com.faeddalberto.nbastats.model.enums.Role;
import com.faeddalberto.nbastats.model.filereader.SeasonFilesReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Loader {

    private static final String TEAMS_FILE_NAME = "teams_conf_div.csv";
    private static final String PLAYERS_FOLDER_NAME = "players";

    public static void main(String args[]) throws IOException {

        if (args == null || args.length < 1) {
            throw new IllegalStateException("Please provide seasons and root folder path: [yyyy-yyyy+1 yyyy-yyyy+1 /root/to/seasons]");
        }

        List<String> seasons = new ArrayList<>();
        String rootPath = null;
        int i = 0;
        for (String seasonOrPath : args) {
            if (i < args.length-2) {
                seasons.add(seasonOrPath);
            } else {
                rootPath = seasonOrPath;
            }
        }

        new Loader().loadSeasons(seasons, rootPath);
    }

    protected void loadSeasons(List<String> seasons, String rootPath) throws IOException {
        SeasonFilesReader seasonFilesReader = new SeasonFilesReader();
        Map<String, Team> teams = seasonFilesReader.readTeams(rootPath);
        Map<String, Player> players = seasonFilesReader.readPlayers(rootPath);

        for (String season : seasons) {
            Map<String, Game> seasonGames = seasonFilesReader.readSeasonGames(rootPath, season, teams);
            Map<String, List<PlayerStatsByGame>> seasonGamesStats = seasonFilesReader.readSeasonGamesStats(rootPath, season, players, teams, seasonGames);
            loadSeason(teams, players, seasonGames, seasonGamesStats);
        }
    }

    private void loadSeason(
            Map<String, Team> teams,
            Map<String, Player> players,
            Map<String, Game> seasonGames,
            Map<String, List<PlayerStatsByGame>> seasonGamesStats) {



    }

    private void buildAndLoadTeamsTables(Map<String, Team> teams, Map<String, List<PlayerStatsByGame>> seasonGamesStats) {

        for (Team team: teams.values()) {
            TeamsByConferenceDivision teamByConfDiv = new TeamsByConferenceDivision(team.getConference(), team.getDivision(), team.getTeamId(), team.getName());

            List<TeamRosterByYearPlayer> teamPlayers = new ArrayList<>();
            for (List<PlayerStatsByGame> playerStatsByGame : seasonGamesStats.values()) {
                List<PlayerStatsByGame> teamPlayersStats = playerStatsByGame.stream()
                        .filter(playersStats -> playersStats.getPlayerTeam().equals(team.getName())).collect(toList());

                teamPlayersStats.stream().map(tps ->
                        new TeamRosterByYearPlayer(
                                tps.getSeason(), team.getName(), tps.getPlayerId(), tps.getPlayerName(), null));
            }
        }

    }

    private void buildAndLoadPlayersTables(Map<String, Player> players) {

    }

    private void buildAndLoadGamesTables(Map<String, Game> seasonGames) {

    }

    private void buildAndLoadPlayerStatsTables(Map<String, List<PlayerStatsByGame>> seasonGamesStats) {

    }
}
