package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.enums.SeasonType;
import com.sun.tools.javac.util.Pair;
import com.sun.xml.internal.ws.model.RuntimeModelerException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class GamesFileReader {

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static Map<String, Team> teamMap;

    public GamesFileReader(Map<String, Team> teamMap) {
        this.teamMap = teamMap;
    }

    public Map<String, Game> readGamesFiles(String folder) throws IOException {
        File[] gamesFiles = new File(folder).listFiles();

        Map<String, Game> gamesMap = new HashMap<>();
        for (File gamesFile : gamesFiles) {
            gamesMap.putAll(readGamesFromFile(gamesFile));
        }
        return gamesMap;
    }

    private Map<String, Game> readGamesFromFile(File gamesFile) throws FileNotFoundException {
        InputStream is = new FileInputStream(gamesFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br.lines()
                .skip(1)
                .map(mapToGame)
                .collect(toMap(gamePair -> gamePair.fst, gamePair -> gamePair.snd));
    }

    private static Function<String, Pair<String, Game>> mapToGame = (line) -> {
        String[] gameSplit = line.split(",");
        int season = Integer.valueOf(gameSplit[1].split("-")[1]);
        SeasonType seasonType = SeasonType.fromString(gameSplit[2].equals("Postseason") ? "Playoffs" : "Regular Season");
        Date gameDate = getGameDate(gameSplit);
        String homeTeam = getTeam(gameSplit[4]);
        int homeTeamScore = Integer.valueOf(gameSplit[5]);
        String visitorTeam = getTeam(gameSplit[6]);
        int visitorTeamScore = Integer.valueOf(gameSplit[7]);
        Game game = new Game(UUID.randomUUID(), season, gameDate, seasonType.name(), homeTeam, homeTeamScore, visitorTeam, visitorTeamScore);
        Pair<String, Game> gamePair = new Pair<>(gameSplit[0], game);
        return gamePair;
    };

    private static Date getGameDate(String[] gameSplit) {
        Date date = null;

        try {
            date = formatter.parse(gameSplit[3]);
        } catch (ParseException e) {
            throw new RuntimeException("error parsing date: " + gameSplit[3]);
        }

        return date;
    }

    private static String getTeam(String teamString) {
        return teamMap.values()
                .stream()
                .filter(team -> containsIgnoreCase(team.getName(), teamString))
                .findFirst()
                .get()
                .getName();
    }

    public static boolean containsIgnoreCase( String fullString, String partial) {
        String[] partialSplit = partial.split(" ");
        boolean contained = false;
        for (String needle : partialSplit) {
            Pattern p = Pattern.compile(needle, Pattern.LITERAL);
            Matcher m = p.matcher(fullString);
            contained = m.find();
        }
        return contained;
    }
}
