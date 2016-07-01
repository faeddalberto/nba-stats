package com.faeddalberto.nbastats.model.loader;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.datastaxrepo.PlayerCareerByNameRepository;
import com.faeddalberto.nbastats.model.datastaxrepo.PlayerRepository;
import com.faeddalberto.nbastats.model.domain.Player;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;
import com.faeddalberto.nbastats.model.enums.Role;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class PlayerTablesLoaderTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME_1 = "player";
    public static final String DATA_TABLE_NAME_2 = "player_career_by_name";

    @Autowired
    private PlayerTablesLoader playerTablesLoader;

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private PlayerCareerByNameRepository playerCareerByNameRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_1));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_2));
    }

    @Test
    public void whenLoadingPlayerWithBatch_thenAvailableOnRetrieval() throws Exception {
        Date dob = new GregorianCalendar(1984, Calendar.DECEMBER, 30).getTime();

        UUID playerId = UUID.randomUUID();
        Player leBron = new Player(playerId, dob, "LeBron James", "Ohio, USA", "2003: 1st Rnd, 1st by CLE");
        PlayerCareerByName leBronCareer04 = new PlayerCareerByName("LeBron James", 2004, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer05 = new PlayerCareerByName("LeBron James", 2005, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer06 = new PlayerCareerByName("LeBron James", 2006, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer07 = new PlayerCareerByName("LeBron James", 2007, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer08 = new PlayerCareerByName("LeBron James", 2008, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer09 = new PlayerCareerByName("LeBron James", 2009, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer10 = new PlayerCareerByName("LeBron James", 2010, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer11 = new PlayerCareerByName("LeBron James", 2011, "Miami Heat", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer12 = new PlayerCareerByName("LeBron James", 2012, "Miami Heat", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer13 = new PlayerCareerByName("LeBron James", 2013, "Miami Heat", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer14 = new PlayerCareerByName("LeBron James", 2014, "Miami Heat", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer15 = new PlayerCareerByName("LeBron James", 2015, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);
        PlayerCareerByName leBronCareer16 = new PlayerCareerByName("LeBron James", 2016, "Cleveland Cavaliers", playerId, "Ohio, USA", dob, "2003: 1st Rnd, 1st by CLE", Role.SHOOTING_GUARD);

        List<PlayerCareerByName> leBronCareer =
                Arrays.asList(leBronCareer04, leBronCareer05, leBronCareer06, leBronCareer07,
                            leBronCareer08, leBronCareer09, leBronCareer10, leBronCareer11, leBronCareer12,
                            leBronCareer13, leBronCareer14, leBronCareer15, leBronCareer16);

        playerTablesLoader.insertPlayerBatch(leBron, leBronCareer);

        Player playerById = playerRepo.getPlayerById(playerId);
        List<PlayerCareerByName> playerCareerByName = playerCareerByNameRepository.getPlayerCareerByName("LeBron James");

        assertTrue(playerById.equals(leBron));
        assertTrue(playerCareerByName.containsAll(leBronCareer));
    }
}
