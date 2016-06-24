package com.faeddalberto.nbastats.model.loader;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.domain.TeamRosterByYearPlayer;
import com.faeddalberto.nbastats.model.domain.TeamsByConferenceDivision;
import com.faeddalberto.nbastats.model.springrepo.TeamRepository;
import com.faeddalberto.nbastats.model.springrepo.TeamRosterByYearRepository;
import com.faeddalberto.nbastats.model.springrepo.TeamsByConferenceDivisionRepository;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.faeddalberto.nbastats.model.enums.Conference.WESTERN_CONFERENCE;
import static com.faeddalberto.nbastats.model.enums.Division.ATLANTIC_DIVISION;
import static com.faeddalberto.nbastats.model.enums.Role.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TeamTablesLoaderTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME_1 = "team";
    public static final String DATA_TABLE_NAME_2 = "teams_by_conference_division";
    public static final String DATA_TABLE_NAME_3 = "team_roster_by_year";

    @Autowired
    private TeamTablesLoader teamTablesLoader;

    @Autowired
    private CassandraConfig cassandraConfig;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamsByConferenceDivisionRepository teamsByConferenceDivisionRepository;

    @Autowired
    private TeamRosterByYearRepository teamRosterByYearRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_1));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_2));
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME_3));
    }

    @Test
    public void whenLoadingTeamsWithBatch_thenAvailableOnRetrieval() throws Exception {
        Team team = new Team("chi", "Chicago Bulls", WESTERN_CONFERENCE.name(), ATLANTIC_DIVISION.name());
        TeamsByConferenceDivision teamsByConferenceDivision = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), ATLANTIC_DIVISION.name(), "chi", "Chicago Bulls");

        TeamRosterByYearPlayer bullsTeamGasol = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Pau Gasol", FORWARD_CENTER.name());
        TeamRosterByYearPlayer bullsTeamRose = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Derrick Rose", POINT_GUARD.name());
        TeamRosterByYearPlayer bullsTeamButler = new TeamRosterByYearPlayer(2016, "Chicago Bulls", UUID.randomUUID(), "Jimmy Butler", SHOOTING_GUARD.name());

        List<TeamRosterByYearPlayer> bullsRoster = Arrays.asList(bullsTeamButler, bullsTeamGasol, bullsTeamRose);

        teamTablesLoader.insertTeamBatch(team, teamsByConferenceDivision, bullsRoster);

        Team insertedTeam = teamRepository.findTeamById("chi");
        TeamsByConferenceDivision insertedTeamByConfDiv = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), ATLANTIC_DIVISION.name()).get(0);
        List<TeamRosterByYearPlayer> insertedTeamPlayers = teamRosterByYearRepository.findTeamRosterByYear(2016, "Chicago Bulls");

        assertEquals(team, insertedTeam);
        assertEquals(teamsByConferenceDivision, insertedTeamByConfDiv);
        assertTrue(insertedTeamPlayers.containsAll(bullsRoster));
    }
}
