package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.enums.Conference;
import com.faeddalberto.nbastats.model.enums.Division;
import org.cassandraunit.shaded.com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TeamRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "team";

    @Autowired
    private TeamRepository teamRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingTeam_thenAvailableOnRetrieval() {
        final Team javaTeam =
                new Team("chi", "Chicago Bulls", Conference.EASTERN_CONFERENCE.name(), Division.ATLANTIC_DIVISION.name());

        teamRepository.save(ImmutableSet.of(javaTeam));

        final Team team = teamRepository.findTeamById("chi");
        assertEquals(javaTeam, team);
    }

    @Test
    public void whenUpdatingTeam_thenAvailableOnRetrieval() {
        final Team javaTeam =
                new Team("chi", "Chicago Bulls", Conference.WESTERN_CONFERENCE.name(), Division.CENTRAL_DIVISION.name());

        teamRepository.save(ImmutableSet.of(javaTeam));

        final Team team = teamRepository.findTeamById("chi");
        assertEquals(javaTeam, team);

        team.setConference(Conference.EASTERN_CONFERENCE.name());
        team.setDivision(Division.ATLANTIC_DIVISION.name());

        teamRepository.save(ImmutableSet.of(team));
        final Team updatedTeam = teamRepository.findTeamById("chi");
        assertEquals(team, updatedTeam);
    }

    @Test(expected = NullPointerException.class)
    public void whenDeletingTeam_thenNotAvailableOnRetrieval() {
        final Team javaTeam =
                new Team("chi", "Chicago Bulls", Conference.WESTERN_CONFERENCE.name(), Division.CENTRAL_DIVISION.name());

        teamRepository.save(ImmutableSet.of(javaTeam));

        final Team team = teamRepository.findTeamById("chi");
        assertEquals(javaTeam, team);

        teamRepository.delete(team);
        final Team deletedTeam = teamRepository.findTeamById("chi");
        assertNotEquals(team.getTeamId(), deletedTeam.getTeamId());
    }

    @Test
    public void whenSavingMultipleTeams_thenAllAvailableOnRetrieval() {
        final Team javaTeam1 = new Team("chi", "Chicago Bulls", Conference.WESTERN_CONFERENCE.name(), Division.ATLANTIC_DIVISION.name());
        final Team javaTeam2 = new Team("gsw", "Golden State Warriors", Conference.EASTERN_CONFERENCE.name(), Division.PACIFIC_DIVISION.name());
        final Team javaTeam3 = new Team("sas", "San Antonio Spurs", Conference.EASTERN_CONFERENCE.name(), Division.SOUTHWEST_DIVISION.name());

        teamRepository.save(ImmutableSet.of(javaTeam1));
        teamRepository.save(ImmutableSet.of(javaTeam2));
        teamRepository.save(ImmutableSet.of(javaTeam3));

        final Iterable<Team> teams = teamRepository.findAll();
        int teamCount = 0;
        for (final Team team : teams) {
            teamCount++;
        }
        assertEquals(teamCount, 3);
    }
}
