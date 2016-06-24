package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.PlayerCareerByName;
import com.faeddalberto.nbastats.model.enums.Role;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerCareerByNameRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "player_career_by_name";

    @Autowired
    private PlayerCareerByNameRepository playerCareerByNameRepository;

    @Autowired
    private CassandraConfig cassandraConfig;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingPlayerCareerByName_thenAvailableOnRetrieval_V1() throws Exception {

        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        PlayerCareerByName playerCareerByName = new PlayerCareerByName("Derrick Rose", 2015, "Chicago Bulls", UUID.randomUUID(), "U.S.A.", dob, "1st in 2009, selected by Chicago", Role.POINT_GUARD);

        mapper.save(playerCareerByName);

        List<PlayerCareerByName> savedPlayer = playerCareerByNameRepository.getPlayerCareerByName(playerCareerByName.getName());

        assertTrue(playerCareerByName.equals(savedPlayer.get(0)));
    }

    @Test
    public void whenSavingPlayerCareerByName_thenAvailableOnRetrieval_V2() throws Exception {

        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        PlayerCareerByName player = new PlayerCareerByName("Derrick Rose", 2015, "Chicago Bulls", UUID.randomUUID(), "USA", dob,  "23rd in 2006, selected by Chicago",Role.POINT_GUARD);

        mapper.save(player);

        List<PlayerCareerByName> savedPlayer = playerCareerByNameRepository.getPlayerInfoByNameAndYear(player.getName(), player.getYear());

        assertTrue(player.equals(savedPlayer.get(0)));
    }


    @Test
    public void whenSavingPlayerCareer_thenAvailableOnRetrievalOrderedByYearDesc() throws Exception {

        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dob = new GregorianCalendar(1984, 31, 12).getTime();

        UUID lebronUUID = UUID.randomUUID();
        PlayerCareerByName playerLeBron2009 = new PlayerCareerByName("LeBron James", 2009, "Cleveland Cavaliers", lebronUUID, "Ohio, USA", dob, "1st in 2004, selected by Cleveland", Role.POINT_GUARD);
        PlayerCareerByName playerLeBron2015 = new PlayerCareerByName("LeBron James", 2015, "Clevelend Cavaliers", lebronUUID, "Ohio, USA", dob, "1st in 2004, selected by Cleveland", Role.POINT_GUARD);
        PlayerCareerByName playerLeBron2012 = new PlayerCareerByName("LeBron James", 2012, "Miami Heat", lebronUUID, "Ohio, USA", dob, "1st in 2004, selected by Cleveland", Role.POINT_GUARD);

        mapper.save(playerLeBron2009);
        mapper.save(playerLeBron2015);
        mapper.save(playerLeBron2012);

        List<PlayerCareerByName> savedPlayer = playerCareerByNameRepository.getPlayerCareerByName("LeBron James");

        assertEquals(3, savedPlayer.size());
        assertEquals(playerLeBron2015, savedPlayer.get(0));
        assertEquals(playerLeBron2012, savedPlayer.get(1));
        assertEquals(playerLeBron2009, savedPlayer.get(2));
    }


    @Test
    public void whenUpdatingPlayer_thenAvailableOnRetrieval() throws Exception {
        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        PlayerCareerByName playerCareerByName = new PlayerCareerByName("Derrick Rose", 2015, "Chicago Bulls", UUID.randomUUID(), "U.S.A.", dob, "31st in 2004, selected by Chicago", Role.POINT_GUARD);

        mapper.save(playerCareerByName);
        List<PlayerCareerByName> savedPlayer = playerCareerByNameRepository.getPlayerCareerByName(playerCareerByName.getName());
        assertTrue(playerCareerByName.equals(savedPlayer.get(0)));

        playerCareerByName.setDraftedInfo("1st pick in 2009, selected by Chicago Bulls");
        playerCareerByName.setCountry("Chicago, Illinois");

        mapper.save(playerCareerByName);
        List<PlayerCareerByName> updatedPlayer = playerCareerByNameRepository.getPlayerCareerByName(playerCareerByName.getName());
        assertEquals(playerCareerByName.getCountry(), updatedPlayer.get(0).getCountry());
        assertEquals(playerCareerByName.getDraftedInfo(), updatedPlayer.get(0).getDraftedInfo());
    }


    @Test
    public void whenDeletingPlayer_thenNotAvailableOnRetrieval() throws Exception {
        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();

        UUID playerId = UUID.randomUUID();
        String playerName = "Derrick Rose";
        PlayerCareerByName playerCareerByName14 = new PlayerCareerByName(playerName, 2014, "Chicago Bulls", playerId, "U.S.A.", dob, "31st in 2004, selected by Chicago", Role.POINT_GUARD);
        PlayerCareerByName playerCareerByName15 = new PlayerCareerByName(playerName, 2015, "Chicago Bulls", playerId, "U.S.A.", dob, "31st in 2004, selected by Chicago", Role.POINT_GUARD);

        mapper.save(playerCareerByName14);
        mapper.save(playerCareerByName15);

        playerCareerByNameRepository.deletePlayerCareer(playerName);

        List<PlayerCareerByName> retrievedPlayer = playerCareerByNameRepository.getPlayerCareerByName(playerName);
        assertTrue(retrievedPlayer.isEmpty());
    }

    @Test
    public void whenSavingMultiplePlayers_thenAllAvailableOnRetrieval() throws Exception {
        Mapper<PlayerCareerByName> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(PlayerCareerByName.class);

        Date dobLBJ = new GregorianCalendar(1987, 4, 21).getTime();
        Date dobRose = new GregorianCalendar(1984, 12, 31).getTime();
        PlayerCareerByName playerLeBron2009 = new PlayerCareerByName("LeBron James", 2009, "Cleveland Cavaliers", UUID.randomUUID(), "Ohio, USA", dobLBJ, "1st in 2004, selected by Cleveland", Role.POINT_GUARD);
        PlayerCareerByName playerDRose2015 = new PlayerCareerByName("Derrick Rose", 2015, "Chicago Bulls", UUID.randomUUID(), "U.S.A.", dobRose, "1st in 2008, selected by Chicago", Role.POINT_GUARD);

        mapper.save(playerLeBron2009);
        mapper.save(playerDRose2015);

        List<PlayerCareerByName> retrievedLBJ = playerCareerByNameRepository.getPlayerCareerByName("LeBron James");
        List<PlayerCareerByName> retrievedDRose = playerCareerByNameRepository.getPlayerCareerByName("Derrick Rose");

        assertEquals(1, retrievedLBJ.size());
        assertTrue(retrievedLBJ.contains(playerLeBron2009));
        assertEquals(1, retrievedDRose.size());
        assertTrue(retrievedDRose.contains(playerDRose2015));
    }


}
