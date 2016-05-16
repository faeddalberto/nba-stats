package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Player;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class PlayersFileReader {

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public Map<String, Player> readPlayerFiles(String folder) throws IOException {
        File[] playerFiles = new File(folder).listFiles();

        Map<String, Player> playerMap = new HashMap<>();
        for (File playerFile : playerFiles) {
            playerMap.put(getOrgPlayerId(playerFile), getPlayerFromFile(playerFile));
        }
        return playerMap;
    }

    private Player getPlayerFromFile(File file) throws FileNotFoundException {
        InputStream is = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br.lines()
                .skip(1)
                .map(mapToPlayer)
                .findFirst()
                .get();
    }

    private static Function<String, Player> mapToPlayer = (line) -> {
        String[] playerSplit = line.split(",");
        Date dob = null;
        try {
            dob = formatter.parse(playerSplit[2]);
        } catch (ParseException e) {}

        return new Player(UUID.randomUUID(), dob, playerSplit[1],  replaceMultiSpace(playerSplit[3]), replaceMultiSpace(playerSplit[4]));
    };

    private static String replaceMultiSpace(String input) {
        return input.replace("  ", " ");
    }

    private String getOrgPlayerId(File playerFile) throws IOException {
        return FilenameUtils.removeExtension(playerFile.getName());
    }
}
