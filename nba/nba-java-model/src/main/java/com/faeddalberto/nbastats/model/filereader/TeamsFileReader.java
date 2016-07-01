package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.enums.Conference;
import com.faeddalberto.nbastats.model.enums.Division;

import java.io.*;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class TeamsFileReader {

    public Map<String, Team> readTeamsFile(String filename) throws FileNotFoundException {

        InputStream is = new FileInputStream(new File(filename));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br.lines()
                .skip(1)
                .map(mapToTeam)
                .collect(toMap(team -> team.getTeamId(), team -> team));
    }

    private static Function<String, Team> mapToTeam = (line) -> {
        String[] teamSplit = line.split(",");

        return new Team(teamSplit[0], teamSplit[1], Conference.fromString(teamSplit[2]).getValue(), Division.fromString(teamSplit[3]).getValue());
    };
}
