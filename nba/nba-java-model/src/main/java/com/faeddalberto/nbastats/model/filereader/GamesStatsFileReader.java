package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.*;
import com.sun.tools.javac.util.Pair;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class GamesStatsFileReader {

    private static Map<String, Player> playerMap;
    private static Map<String, Team> teamMap;
    private static Map<String, Game> gameMap;


    public GamesStatsFileReader(Map<String, Player> playerMap, Map<String, Team> teamMap, Map<String, Game> gameMap) {
        this.playerMap = playerMap;
        this.teamMap = teamMap;
        this.gameMap = gameMap;
    }

    public Map<String, List<PlayerStatsByGame>> readGamesStatsFiles(String folder) throws IOException {
        File[] gameStatsFiles = new File(folder).listFiles();

        Map<String, List<PlayerStatsByGame>> gamesStatsMap = new HashMap<>();
        for (File gameStatsFile : gameStatsFiles) {
            gamesStatsMap.putAll(readPlayerStatsFromFile(gameStatsFile));
        }
        return gamesStatsMap;
    }

    private Map<String, List<PlayerStatsByGame>> readPlayerStatsFromFile(File gameStatsFile) throws IOException {
        InputStream is = new FileInputStream(gameStatsFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br.lines()
                .skip(1)
                .map(mapToPlayerStats)
                .collect(groupingBy(gamePair-> gamePair.fst,
                        mapping(gamePair -> gamePair.snd, toList())));
    }

    private static Function<String, Pair<String, PlayerStatsByGame>> mapToPlayerStats = (line) -> {
        String[] playerStatsSplit = line.split(",");
        Game game = gameMap.get(playerStatsSplit[0]);

        UUID gameId = game.getGameId();
        Date gameDate = game.getDate();
        UUID playerId = playerMap.get(playerStatsSplit[1]).getPlayerId();
        int season = Integer.valueOf(playerStatsSplit[2]);
        String playerName = playerMap.get(playerStatsSplit[1]).getName();
        String playerTeam = getTeam(playerStatsSplit[4], season);
        String playerRole = playerStatsSplit[5];
        int minutesPlayed = Integer.valueOf(playerStatsSplit[6]);
        MadeAttemptedStat fieldGoals = getMadeAttemptedStat(playerStatsSplit[7]);
        MadeAttemptedStat threePoints = getMadeAttemptedStat(playerStatsSplit[8]);
        MadeAttemptedStat freeThrows = getMadeAttemptedStat(playerStatsSplit[9]);
        int offRebs = Integer.valueOf(playerStatsSplit[10]);
        int defRebs = Integer.valueOf(playerStatsSplit[11]);
        int totRebs = Integer.valueOf(playerStatsSplit[12]);
        int assists = Integer.valueOf(playerStatsSplit[13]);
        int steals = Integer.valueOf(playerStatsSplit[14]);
        int blocks = Integer.valueOf(playerStatsSplit[15]);
        int turnovers = Integer.valueOf(playerStatsSplit[16]);
        int personalFauls = Integer.valueOf(playerStatsSplit[17]);
        int plusMinus = Integer.valueOf(playerStatsSplit[18]);
        int points = Integer.valueOf(playerStatsSplit[19]);

        PlayerStatsByGame playerStatsByGame = new PlayerStatsByGame(
                gameId, gameDate, playerId, season, playerTeam, playerRole, getOpponentTeam(Integer.valueOf(playerStatsSplit[0]), playerTeam),
                minutesPlayed, playerName, fieldGoals, threePoints, freeThrows, offRebs, defRebs, totRebs,
                assists, steals, blocks, turnovers, personalFauls, plusMinus, points);

        Pair<String, PlayerStatsByGame> statsPair = new Pair<>(playerStatsSplit[0], playerStatsByGame);
        return statsPair;
    };

    private static String getTeam(String teamString, int season) {
        String teamCut = season < 2016 && teamString.equals("Hornets") ? "New Orleans" :
                        season < 2016 && teamString.equals("Bobcats") ? "Charlotte" :
                                teamString;

        return teamMap.values()
                .stream()
                .filter(team -> team.getName().contains(teamCut))
                .findFirst()
                .get()
                .getName();
    }

    private static MadeAttemptedStat getMadeAttemptedStat(String madeAttemptedStat) {
        String[] stat = madeAttemptedStat.split("-");
        return new MadeAttemptedStat(Integer.valueOf(stat[1]), Integer.valueOf(stat[0]));
    }

    private static String getOpponentTeam(int gameId, String playerTeam) {
        Game game = gameMap.get(String.valueOf(gameId));
        if (game.getHomeTeam().equals(playerTeam)) {
            return game.getVisitorTeam();
        } else
            return game.getHomeTeam();
    }
}
