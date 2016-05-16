package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.enums.Conference;
import com.faeddalberto.nbastats.model.enums.Division;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TeamsFileReaderTest {

    @Test
    public void whenReadingTeamsFileInfo_thenTeamssMapIsReturned() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        TeamsFileReader teamsFileReader = new TeamsFileReader();
        Map<String, Team> teamsMap = teamsFileReader.readTeamsFile(classLoader.getResource("teams_conf_div.csv").getPath());

        assertEquals(30, teamsMap.size());
        assertTrue(teamsMap.containsKey("bos"));
        assertTrue(teamsMap.containsKey("bkn"));
        assertTrue(teamsMap.containsKey("nyk"));
        assertTrue(teamsMap.containsKey("phi"));
        assertTrue(teamsMap.containsKey("tor"));
        assertTrue(teamsMap.containsKey("gsw"));
        assertTrue(teamsMap.containsKey("lac"));
        assertTrue(teamsMap.containsKey("lal"));
        assertTrue(teamsMap.containsKey("phx"));
        assertTrue(teamsMap.containsKey("sac"));
        assertTrue(teamsMap.containsKey("chi"));
        assertTrue(teamsMap.containsKey("cle"));
        assertTrue(teamsMap.containsKey("det"));
        assertTrue(teamsMap.containsKey("ind"));
        assertTrue(teamsMap.containsKey("mil"));
        assertTrue(teamsMap.containsKey("dal"));
        assertTrue(teamsMap.containsKey("hou"));
        assertTrue(teamsMap.containsKey("mem"));
        assertTrue(teamsMap.containsKey("nop"));
        assertTrue(teamsMap.containsKey("sas"));
        assertTrue(teamsMap.containsKey("atl"));
        assertTrue(teamsMap.containsKey("cha"));
        assertTrue(teamsMap.containsKey("mia"));
        assertTrue(teamsMap.containsKey("orl"));
        assertTrue(teamsMap.containsKey("wsh"));
        assertTrue(teamsMap.containsKey("den"));
        assertTrue(teamsMap.containsKey("min"));
        assertTrue(teamsMap.containsKey("okc"));
        assertTrue(teamsMap.containsKey("por"));
        assertTrue(teamsMap.containsKey("uth"));

        Team chi = new Team("chi", "Chicago Bulls", Conference.EASTERN_CONFERENCE.name(), Division.CENTRAL_DIVISION.name());
        assertTrue(teamsMap.get("chi").equals(chi));
    }
}