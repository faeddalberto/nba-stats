package com.faeddalberto.nbastats.model.filereader;

import com.faeddalberto.nbastats.model.domain.Player;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayersFileReaderTest {

    @Test
    public void whenReadingPlayerFilesInfo_thenPlayersMapIsReturned() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        PlayersFileReader playersFileReader = new PlayersFileReader();
        Map<String, Player> playerMap = playersFileReader.readPlayerFiles(classLoader.getResource("seasons/players").getPath());

        assertEquals(26, playerMap.size());
        assertTrue(playerMap.containsKey("1994"));
        assertTrue(playerMap.containsKey("2011"));
        assertTrue(playerMap.containsKey("2767"));
        assertTrue(playerMap.containsKey("3015"));
        assertTrue(playerMap.containsKey("3028"));
        assertTrue(playerMap.containsKey("3213"));
        assertTrue(playerMap.containsKey("3233"));
        assertTrue(playerMap.containsKey("3247"));
        assertTrue(playerMap.containsKey("4003"));
        assertTrue(playerMap.containsKey("4015"));
        assertTrue(playerMap.containsKey("6443"));
        assertTrue(playerMap.containsKey("6454"));
        assertTrue(playerMap.containsKey("6462"));
        assertTrue(playerMap.containsKey("6585"));
        assertTrue(playerMap.containsKey("6622"));
        assertTrue(playerMap.containsKey("6637"));
        assertTrue(playerMap.containsKey("2284101"));
        assertTrue(playerMap.containsKey("2488721"));
        assertTrue(playerMap.containsKey("2490089"));
        assertTrue(playerMap.containsKey("2528779"));
        assertTrue(playerMap.containsKey("2578259"));
        assertTrue(playerMap.containsKey("2580782"));
        assertTrue(playerMap.containsKey("2581018"));
        assertTrue(playerMap.containsKey("2968439"));
        assertTrue(playerMap.containsKey("3032979"));
        assertTrue(playerMap.containsKey("3134881"));

        assertTrue(playerMap.get("2284101").getPlayerId() instanceof UUID);
        assertEquals("Justin Holiday", playerMap.get("2284101").getName());
        GregorianCalendar cal0 = new GregorianCalendar(1989, Calendar.APRIL, 05);
        assertEquals(cal0.getTime(), playerMap.get("2284101").getDateOfBirth());
        assertEquals("Mission Hills - CA", playerMap.get("2284101").getCountry());
        assertEquals("n.a.", playerMap.get("2284101").getDraftedInfo());

        assertTrue(playerMap.get("2011").getPlayerId() instanceof UUID);
        assertEquals("Kyle Korver", playerMap.get("2011").getName());
        GregorianCalendar cal1 = new GregorianCalendar(1981, Calendar.MARCH, 17);
        assertEquals(cal1.getTime(), playerMap.get("2011").getDateOfBirth());
        assertEquals("Lakewood - CA", playerMap.get("2011").getCountry());
        assertEquals("2003: 2nd Rnd 51st by NJ", playerMap.get("2011").getDraftedInfo());
    }
}
