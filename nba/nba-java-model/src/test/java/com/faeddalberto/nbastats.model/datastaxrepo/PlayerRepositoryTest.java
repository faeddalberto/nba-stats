package com.faeddalberto.nbastats.model.datastaxrepo;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.Player;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class PlayerRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "player";

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private CassandraConfig cassandraConfig;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingPlayer_thenAvailableOnRetrieval_V1() throws Exception {

        Mapper<Player> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(Player.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        Player player = new Player(UUID.randomUUID(), dob, "Derrick Rose", "USA", "23rd in 2006, selected by Chicago");

        mapper.save(player);

        List<Player> savedPlayer = playerRepo.getAllPlayers();

        assertTrue(player.equals(savedPlayer.get(0)));
    }

    @Test
    public void whenSavingPlayer_thenAvailableOnRetrieval_V2() throws Exception {

        Mapper<Player> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(Player.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        Player player = new Player(UUID.randomUUID(), dob, "Derrick Rose", "USA", "23rd in 2006, selected by Chicago");

        mapper.save(player);

        Player savedPlayer = playerRepo.getPlayerById(player.getPlayerId());

        assertTrue(player.equals(savedPlayer));
    }

    @Test
    public void whenUpdatingPlayer_thenAvailableOnRetrieval() throws Exception {
        Mapper<Player> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(Player.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        Player player = new Player(UUID.randomUUID(), dob, "Derrick Rose", "USA", "23rd in 2006, selected by Chicago");

        mapper.save(player);
        Player savedPlayer = playerRepo.getPlayerById(player.getPlayerId());
        assertTrue(player.equals(savedPlayer));

        player.setDraftedInfo("1st pick in 2009, selected by Chicago Bulls");
        player.setCountry("Chicago, Illinois");

        mapper.save(player);
        Player updatedPlayer = playerRepo.getPlayerById(player.getPlayerId());
        assertEquals(player.getCountry(), updatedPlayer.getCountry());
        assertEquals(player.getDraftedInfo(), updatedPlayer.getDraftedInfo());
    }

    @Test
    public void whenDeletingPlayer_thenNotAvailableOnRetrieval() throws Exception {
        Mapper<Player> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(Player.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        Player player = new Player(UUID.randomUUID(), dob, "Derrick Rose", "USA", "23rd in 2006, selected by Chicago");

        mapper.save(player);
        mapper.delete(player.getPlayerId());

        Player retrievedPlayer = playerRepo.getPlayerById(player.getPlayerId());
        assertTrue(retrievedPlayer == null);
    }

    @Test
    public void whenSavingMultiplePlayers_thenAllAvailableOnRetrieval() throws Exception {
        Mapper<Player> mapper = new MappingManager(cassandraConfig.session().getObject()).mapper(Player.class);

        Date dob = new GregorianCalendar(1987, 4, 21).getTime();
        Player playerDRose = new Player(UUID.randomUUID(), dob, "Derrick Rose", "Illinois, USA", "23rd in 2006, selected by Chicago");
        Player playerLeBron = new Player(UUID.randomUUID(), dob, "LeBron James", "Ohio, USA", "1st in 2004, selected by Cleveland");

        mapper.save(playerDRose);
        mapper.save(playerLeBron);

        List<Player> retrievedPlayers = playerRepo.getAllPlayers();

        assertEquals(2, retrievedPlayers.size());
        assertTrue(retrievedPlayers.contains(playerDRose));
        assertTrue(retrievedPlayers.contains(playerLeBron));
    }
}