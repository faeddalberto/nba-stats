package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.domain.TeamRosterByYearPlayer;
import com.faeddalberto.nbastats.model.enums.Role;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class TeamRosterByYearPlayerRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "team_roster_by_year";

    @Autowired
    private TeamRosterByYearRepository teamRosterByYearRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingTeamRoster_thenAvailableOnRetrieval() {
        final TeamRosterByYearPlayer kobeLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Kobe Bryant", Role.SHOOTING_GUARD.name());
        final TeamRosterByYearPlayer russellLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "DeAngelo Russell", Role.GUARD.name());
        final TeamRosterByYearPlayer metaLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Meta World Peace", Role.SMALL_FORWARD.name());

        teamRosterByYearRepository.save(kobeLA);
        teamRosterByYearRepository.save(russellLA);
        teamRosterByYearRepository.save(metaLA);

        List<TeamRosterByYearPlayer> laPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Los Angeles Lakers");
        assertEquals(3, laPlayers.size());
        assertTrue(laPlayers.contains(kobeLA));
        assertTrue(laPlayers.contains(russellLA));
        assertTrue(laPlayers.contains(metaLA));
    }

    @Test
    public void whenUpdatingTeamRoster_thenAvailableOnRetrieval() {
        final TeamRosterByYearPlayer drose = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Derrick Rose", Role.SHOOTING_GUARD.name());
        final TeamRosterByYearPlayer jbutler = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Jimmy Butler", Role.GUARD.name());
        final TeamRosterByYearPlayer pgasol = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Pau Gasol", Role.FORWARD_CENTER.name());

        teamRosterByYearRepository.save(drose);
        teamRosterByYearRepository.save(jbutler);
        teamRosterByYearRepository.save(pgasol);

        List<TeamRosterByYearPlayer> chiPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Chicago Bulls");
        assertEquals(3, chiPlayers.size());
        assertTrue(chiPlayers.contains(drose));
        assertTrue(chiPlayers.contains(jbutler));
        assertTrue(chiPlayers.contains(pgasol));

        pgasol.setRole(Role.CENTER.name());
        teamRosterByYearRepository.save(pgasol);
        List<TeamRosterByYearPlayer> updatedChiPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Chicago Bulls");
        assertTrue(updatedChiPlayers.contains(pgasol));
    }

    @Test
    public void whenDeletingTeamRoster_thenNotAvailableOnRetrieval() {
        final TeamRosterByYearPlayer kobeLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Kobe Bryant", Role.SHOOTING_GUARD.name());
        final TeamRosterByYearPlayer russellLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "DeAngelo Russell", Role.GUARD.name());
        final TeamRosterByYearPlayer metaLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Meta World Peace", Role.SMALL_FORWARD.name());

        teamRosterByYearRepository.save(kobeLA);
        teamRosterByYearRepository.save(russellLA);
        teamRosterByYearRepository.save(metaLA);

        MapId id = BasicMapId.id().with("year", 2016).with("team", "Los Angeles Lakers");

        teamRosterByYearRepository.delete(id);

        List<TeamRosterByYearPlayer> laPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Los Angeles Lakers");
        assertFalse(laPlayers.contains(kobeLA));
    }

    @Test
    public void whenSavingMultipleTeamRosters_thenAllAvailableOnRetrieval() {
        final TeamRosterByYearPlayer kobeLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Kobe Bryant", Role.SHOOTING_GUARD.name());
        final TeamRosterByYearPlayer russellLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "DeAngelo Russell", Role.GUARD.name());
        final TeamRosterByYearPlayer metaLA = new TeamRosterByYearPlayer(2016, "Los Angeles Lakers", UUID.randomUUID(), "Meta World Peace", Role.SMALL_FORWARD.name());
        final TeamRosterByYearPlayer drose = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Derrick Rose", Role.SHOOTING_GUARD.name());
        final TeamRosterByYearPlayer jbutler = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Jimmy Butler", Role.GUARD.name());
        final TeamRosterByYearPlayer pgasol = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Pau Gasol", Role.FORWARD_CENTER.name());

        teamRosterByYearRepository.save(kobeLA);
        teamRosterByYearRepository.save(russellLA);
        teamRosterByYearRepository.save(metaLA);
        teamRosterByYearRepository.save(drose);
        teamRosterByYearRepository.save(jbutler);
        teamRosterByYearRepository.save(pgasol);

        List<TeamRosterByYearPlayer> laPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Los Angeles Lakers");
        List<TeamRosterByYearPlayer> chiPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Chicago Bulls");

        assertEquals(3, laPlayers.size());
        assertTrue(laPlayers.contains(kobeLA));
        assertTrue(laPlayers.contains(russellLA));
        assertTrue(laPlayers.contains(metaLA));
        assertEquals(3, chiPlayers.size());
        assertTrue(chiPlayers.contains(drose));
        assertTrue(chiPlayers.contains(jbutler));
        assertTrue(chiPlayers.contains(pgasol));
    }
}
