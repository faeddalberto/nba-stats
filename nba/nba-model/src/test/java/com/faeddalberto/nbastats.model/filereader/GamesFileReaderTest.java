package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Game;
import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.enums.SeasonType;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GamesFileReaderTest {

    @Test
    public void whenReadingGamesFileInfo_thenGamesMapIsReturned() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        GamesFileReader gamesFileReader =
                new GamesFileReader(getTeams(classLoader));
        Map<String, Game> gamesMap = gamesFileReader.readGamesFiles(classLoader.getResource("2015-2016_season/games").getPath());

        assertEquals(82, gamesMap.size());
        assertTrue(gamesMap.containsKey("400827888"));
        assertTrue(gamesMap.containsKey("400827906"));
        assertTrue(gamesMap.containsKey("400827913"));
        assertTrue(gamesMap.containsKey("400827926"));
        assertTrue(gamesMap.containsKey("400827942"));
        assertTrue(gamesMap.containsKey("400827949"));
        assertTrue(gamesMap.containsKey("400827968"));
        assertTrue(gamesMap.containsKey("400827973"));
        assertTrue(gamesMap.containsKey("400827988"));
        assertTrue(gamesMap.containsKey("400828004"));
        assertTrue(gamesMap.containsKey("400828016"));
        assertTrue(gamesMap.containsKey("400828035"));
        assertTrue(gamesMap.containsKey("400828046"));
        assertTrue(gamesMap.containsKey("400828056"));
        assertTrue(gamesMap.containsKey("400828078"));
        assertTrue(gamesMap.containsKey("400828097"));
        assertTrue(gamesMap.containsKey("400828109"));
        assertTrue(gamesMap.containsKey("400828120"));
        assertTrue(gamesMap.containsKey("400828129"));
        assertTrue(gamesMap.containsKey("400828143"));
        assertTrue(gamesMap.containsKey("400828159"));
        assertTrue(gamesMap.containsKey("400828173"));
        assertTrue(gamesMap.containsKey("400828215"));
        assertTrue(gamesMap.containsKey("400828218"));
        assertTrue(gamesMap.containsKey("400828234"));
        assertTrue(gamesMap.containsKey("400828247"));
        assertTrue(gamesMap.containsKey("400828263"));
        assertTrue(gamesMap.containsKey("400828276"));
        assertTrue(gamesMap.containsKey("400828294"));
        assertTrue(gamesMap.containsKey("400828300"));
        assertTrue(gamesMap.containsKey("400828316"));
        assertTrue(gamesMap.containsKey("400828329"));
        assertTrue(gamesMap.containsKey("400828346"));
        assertTrue(gamesMap.containsKey("400828357"));
        assertTrue(gamesMap.containsKey("400828393"));
        assertTrue(gamesMap.containsKey("400828408"));
        assertTrue(gamesMap.containsKey("400828423"));
        assertTrue(gamesMap.containsKey("400828438"));
        assertTrue(gamesMap.containsKey("400828464"));
        assertTrue(gamesMap.containsKey("400828486"));
        assertTrue(gamesMap.containsKey("400828489"));
        assertTrue(gamesMap.containsKey("400828507"));
        assertTrue(gamesMap.containsKey("400828526"));
        assertTrue(gamesMap.containsKey("400828530"));
        assertTrue(gamesMap.containsKey("400828547"));
        assertTrue(gamesMap.containsKey("400828559"));
        assertTrue(gamesMap.containsKey("400828574"));
        assertTrue(gamesMap.containsKey("400828579"));
        assertTrue(gamesMap.containsKey("400828602"));
        assertTrue(gamesMap.containsKey("400828610"));
        assertTrue(gamesMap.containsKey("400828623"));
        assertTrue(gamesMap.containsKey("400828637"));
        assertTrue(gamesMap.containsKey("400828658"));
        assertTrue(gamesMap.containsKey("400828667"));
        assertTrue(gamesMap.containsKey("400828683"));
        assertTrue(gamesMap.containsKey("400828695"));
        assertTrue(gamesMap.containsKey("400828706"));
        assertTrue(gamesMap.containsKey("400828723"));
        assertTrue(gamesMap.containsKey("400828752"));
        assertTrue(gamesMap.containsKey("400828765"));
        assertTrue(gamesMap.containsKey("400828782"));
        assertTrue(gamesMap.containsKey("400828808"));
        assertTrue(gamesMap.containsKey("400828816"));
        assertTrue(gamesMap.containsKey("400828834"));
        assertTrue(gamesMap.containsKey("400828846"));
        assertTrue(gamesMap.containsKey("400828861"));
        assertTrue(gamesMap.containsKey("400828871"));
        assertTrue(gamesMap.containsKey("400828894"));
        assertTrue(gamesMap.containsKey("400828902"));
        assertTrue(gamesMap.containsKey("400828916"));
        assertTrue(gamesMap.containsKey("400828935"));
        assertTrue(gamesMap.containsKey("400828943"));
        assertTrue(gamesMap.containsKey("400828963"));
        assertTrue(gamesMap.containsKey("400828972"));
        assertTrue(gamesMap.containsKey("400828986"));
        assertTrue(gamesMap.containsKey("400829000"));
        assertTrue(gamesMap.containsKey("400829018"));
        assertTrue(gamesMap.containsKey("400829044"));
        assertTrue(gamesMap.containsKey("400829060"));
        assertTrue(gamesMap.containsKey("400829075"));
        assertTrue(gamesMap.containsKey("400829090"));
        assertTrue(gamesMap.containsKey("400829108"));

        assertEquals(2016, gamesMap.get("400828935").getSeason());
        assertEquals(SeasonType.REGULAR_SEASON.name(), gamesMap.get("400828935").getSeasonType());
        GregorianCalendar gameDate = new GregorianCalendar(2016, Calendar.MARCH, 21);
        assertEquals(gameDate.getTime(), gamesMap.get("400828935").getDate());
        assertEquals("Washington Wizards", gamesMap.get("400828935").getHomeTeam());
        assertEquals(102, gamesMap.get("400828935").getHomeTeamScore());
        assertEquals("Atlanta Hawks", gamesMap.get("400828935").getVisitorTeam());
        assertEquals(117, gamesMap.get("400828935").getVisitorTeamScore());
    }

    Map<String, Team> getTeams(ClassLoader classLoader) throws FileNotFoundException {
        TeamsFileReader teamsFileReader = new TeamsFileReader();
        return teamsFileReader.readTeamsFile(classLoader.getResource("teams_conf_div.csv").getPath());
    }

}