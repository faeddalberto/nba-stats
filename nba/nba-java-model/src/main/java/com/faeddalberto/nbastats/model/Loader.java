package com.faeddalberto.nbastats.model;

import com.faeddalberto.nbastats.model.domain.*;
import com.faeddalberto.nbastats.model.enums.Role;
import com.faeddalberto.nbastats.model.filereader.SeasonFilesReader;
import com.faeddalberto.nbastats.model.loader.GameTablesLoader;
import com.faeddalberto.nbastats.model.loader.PlayerTablesLoader;
import com.faeddalberto.nbastats.model.loader.StatsTablesLoader;
import com.faeddalberto.nbastats.model.loader.TeamTablesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class Loader {

    private static final String TEAMS_FILE_NAME = "teams_conf_div.csv";
    private static final String PLAYERS_FOLDER_NAME = "players";

    @Autowired
    private GameTablesLoader gameTablesLoader;

    @Autowired
    private PlayerTablesLoader playerTablesLoader;

    @Autowired
    private StatsTablesLoader statsTablesLoader;

    @Autowired
    private TeamTablesLoader teamTablesLoader;

    private List<String> seasons;

    private String rootPath;

    public static void main(String args[]) throws Exception {

        if (args == null || args.length < 2) {
            throw new IllegalStateException("Please provide seasons and root folder path: [yyyy-yyyy+1 yyyy-yyyy+1 /root/to/seasons]");
        }

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-config.xml");

        Loader loader = context.getBean(Loader.class);
        loader.getArguments(args).loadSeasons();

        System.exit(0);
    }

    protected Loader getArguments(String[] args) {
        seasons = new ArrayList<>();
        int i = 0;
        for (String seasonOrPath : args) {
            if (i < args.length-1) {
                seasons.add(seasonOrPath);
            } else {
                rootPath = seasonOrPath;
            }
            i++;
        }
        return this;
    }

    protected void loadSeasons() throws Exception {
        SeasonFilesReader seasonFilesReader = new SeasonFilesReader();
        Map<String, Team> teams = seasonFilesReader.readTeams(rootPath);
        Map<String, Player> players = seasonFilesReader.readPlayers(rootPath);

        Map<String, Game> allSeasonsGames = new HashMap<>();
        Map<String, List<PlayerStatsByGame>> allSeasonsStats = new HashMap<>();
        for (String season : seasons) {
            allSeasonsGames.putAll(seasonFilesReader.readSeasonGames(rootPath, season, teams));
            allSeasonsStats.putAll(seasonFilesReader.readSeasonGamesStats(rootPath, season, players, teams, allSeasonsGames));
        }
        loadSeason(teams, players, allSeasonsGames, allSeasonsStats);
    }

    protected void loadSeason(
            Map<String, Team> teams,
            Map<String, Player> players,
            Map<String, Game> seasonGames,
            Map<String, List<PlayerStatsByGame>> seasonGamesStats) throws Exception {

        buildAndLoadTeamsTables(teams, seasonGamesStats);
        buildAndLoadPlayersTables(players, seasonGamesStats);
        buildAndLoadGamesTables(seasonGames);
        buildAndLoadPlayerStatsTables(seasonGamesStats);
    }

    protected void buildAndLoadTeamsTables(Map<String, Team> teams, Map<String, List<PlayerStatsByGame>> seasonGamesStats) throws Exception {

        for (Team team: teams.values()) {
            TeamsByConferenceDivision teamByConfDiv = new TeamsByConferenceDivision(team.getConference(), team.getDivision(), team.getTeamId(), team.getName());

            List<TeamRosterByYearPlayer> teamPlayers = new ArrayList<>();
            for (List<PlayerStatsByGame> playerStatsByGame : seasonGamesStats.values()) {
                List<PlayerStatsByGame> teamPlayersStats = playerStatsByGame.stream()
                        .filter(playersStats -> playersStats.getPlayerTeam().equals(team.getName()))
                        .collect(toList());

                teamPlayers.addAll(teamPlayersStats.stream().map(tps ->
                        new TeamRosterByYearPlayer(
                                tps.getSeason(),
                                team.getName(),
                                tps.getPlayerId(),
                                tps.getPlayerName(),
                                tps.getPlayerRole()))
                        .collect(Collectors.toList()));
            }
            teamTablesLoader.insertTeamBatch(team, teamByConfDiv, teamPlayers);
        }

    }

    protected void buildAndLoadPlayersTables(Map<String, Player> players, Map<String, List<PlayerStatsByGame>> seasonGamesStats) throws Exception {

        for (Player player : players.values()) {

            List<PlayerCareerByName> playerCareerByName = new ArrayList<>();
            for (List<PlayerStatsByGame> playerStatsByGame : seasonGamesStats.values()) {
                List<PlayerStatsByGame> teamPlayersStats = playerStatsByGame.stream()
                        .filter(playersStats -> playersStats.getPlayerId().equals(player.getPlayerId()))
                        .collect(toList());

                playerCareerByName.addAll(teamPlayersStats.stream().map(tps ->
                        new PlayerCareerByName(
                                tps.getPlayerName(),
                                tps.getSeason(),
                                tps.getPlayerTeam(),
                                tps.getPlayerId(),
                                player.getCountry(),
                                player.getDateOfBirth(),
                                player.getDraftedInfo(),
                                Role.fromShortName(tps.getPlayerRole())))
                        .collect(Collectors.toList()));
            }

            playerTablesLoader.insertPlayerBatch(player, playerCareerByName);
        }
    }

    protected void buildAndLoadGamesTables(Map<String, Game> seasonGames) throws Exception {

        for (Game game : seasonGames.values()) {
            GameBySeasonTeams gameBySeasonTeams =
                    new GameBySeasonTeams(
                            game.getSeason(),
                            game.getHomeTeam(),
                            game.getVisitorTeam(),
                            game.getDate(),
                            game.getGameId(),
                            game.getHomeTeamScore(),
                            game.getVisitorTeamScore(),
                            game.getSeasonType());

            gameTablesLoader.insertGameBatch(game, gameBySeasonTeams);
        }
    }

    protected void buildAndLoadPlayerStatsTables(Map<String, List<PlayerStatsByGame>> seasonGamesStats) throws Exception {

        for (List<PlayerStatsByGame> playersStatsByGames : seasonGamesStats.values()) {

            for (PlayerStatsByGame psg :playersStatsByGames) {
                PlayerStatsByOpponent playerStatsByOpponent =
                        new PlayerStatsByOpponent(
                                psg.getOpponentTeam(),
                                psg.getPlayerName(),
                                psg.getPlayerTeam(),
                                psg.getSeason(),
                                psg.getDate(),
                                psg.getGameId(),
                                psg.getPlayerId(),
                                psg.getMinsPlayed(),
                                psg.getFieldGoals(),
                                psg.getThreePoints(),
                                psg.getFreeThrows(),
                                psg.getOffensiveRebounds(),
                                psg.getDefensiveRebounds(),
                                psg.getTotalRebounds(),
                                psg.getAssists(),
                                psg.getSteals(),
                                psg.getBlocks(),
                                psg.getTurnovers(),
                                psg.getPersonalFauls(),
                                psg.getPlusMinus(),
                                psg.getPoints());

                StatsBySeason statsBySeason =
                        new StatsBySeason(
                                psg.getSeason(),
                                getMonth(psg.getDate()),
                                psg.getPlayerTeam(),
                                psg.getPlayerName(),
                                psg.getOpponentTeam(),
                                psg.getGameId(),
                                psg.getPlayerId(),
                                psg.getDate(),
                                psg.getMinsPlayed(),
                                psg.getFieldGoals(),
                                psg.getThreePoints(),
                                psg.getFreeThrows(),
                                psg.getOffensiveRebounds(),
                                psg.getDefensiveRebounds(),
                                psg.getTotalRebounds(),
                                psg.getAssists(),
                                psg.getSteals(),
                                psg.getBlocks(),
                                psg.getTurnovers(),
                                psg.getPersonalFauls(),
                                psg.getPlusMinus(),
                                psg.getPoints());

                statsTablesLoader.insertStatsBatch(psg, playerStatsByOpponent, statsBySeason);
            }
        }
    }

    private int getMonth(Date gameDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(gameDate);
        int month = cal.get(Calendar.MONTH) + 1;

        return month;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public String getRootPath() {
        return rootPath;
    }

}
