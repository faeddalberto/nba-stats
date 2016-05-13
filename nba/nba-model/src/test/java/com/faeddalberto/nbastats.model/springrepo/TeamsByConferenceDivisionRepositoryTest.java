package com.faeddalberto.nbastats.model.springrepo;

import com.faeddalberto.nbastats.model.CassandraIntegration;
import com.faeddalberto.nbastats.model.domain.TeamsByConferenceDivision;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;

import java.util.List;

import static com.faeddalberto.nbastats.model.enums.Conference.EASTERN_CONFERENCE;
import static com.faeddalberto.nbastats.model.enums.Conference.WESTERN_CONFERENCE;
import static com.faeddalberto.nbastats.model.enums.Division.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TeamsByConferenceDivisionRepositoryTest extends CassandraIntegration {

    public static final String DATA_TABLE_NAME = "teams_by_conference_division";

    @Autowired
    private TeamsByConferenceDivisionRepository teamsByConferenceDivisionRepository;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @Test
    public void whenSavingTeam_thenAvailableOnRetrieval_V1() {
        TeamsByConferenceDivision gswTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name(), "gsw", "Golden State Warriors");

        teamsByConferenceDivisionRepository.save(gswTeam);

        List<TeamsByConferenceDivision> teams = teamsByConferenceDivisionRepository.findTeamsByConference(WESTERN_CONFERENCE.name());

        assertEquals(1, teams.size());
        assertTrue(teams.contains(gswTeam));
    }

    @Test
    public void whenSavingTeam_thenAvailableOnRetrieval_V2() {
        TeamsByConferenceDivision gswTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name(), "gsw", "Golden State Warriors");

        teamsByConferenceDivisionRepository.save(gswTeam);

        List<TeamsByConferenceDivision> teams = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name());

        assertEquals(1, teams.size());
        assertTrue(teams.contains(gswTeam));
    }

    @Test
    public void whenUpdatingTeam_thenAvailableOnRetrieval() {
        TeamsByConferenceDivision gswTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name(), "gsw", "Golden State");

        teamsByConferenceDivisionRepository.save(gswTeam);

        List<TeamsByConferenceDivision> teams = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name());

        assertEquals(1, teams.size());
        assertTrue(teams.contains(gswTeam));

        gswTeam.setName("Golden State Warriors");

        teamsByConferenceDivisionRepository.save(gswTeam);

        List<TeamsByConferenceDivision> updatedTeams = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name());

        assertEquals(1, teams.size());
        assertTrue(updatedTeams.contains(gswTeam));
    }

    @Test
    public void whenDeletingTeam_thenNotAvailableOnRetrieval() {
        TeamsByConferenceDivision gswTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name(), "gsw", "Golden State Warriors");

        teamsByConferenceDivisionRepository.save(gswTeam);
        List<TeamsByConferenceDivision> teams = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name());
        assertTrue(teams.contains(gswTeam));

        MapId id = BasicMapId.id().with("conference", WESTERN_CONFERENCE.name());
        teamsByConferenceDivisionRepository.delete(id);
        List<TeamsByConferenceDivision> teamsAfterDeletion = teamsByConferenceDivisionRepository.findTeamsByConferenceAndDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name());
        assertFalse(teamsAfterDeletion.contains(gswTeam));
    }

    @Test
    public void whenSavingMultipleTeams_thenAllAvailableOnRetrieval() {
        TeamsByConferenceDivision gswTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), PACIFIC_DIVISION.name(), "gsw", "Golden State Warriors");
        TeamsByConferenceDivision okcTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), NORTHWEST_DIVISION.name(), "okc", "Oklahoma City Thunder");
        TeamsByConferenceDivision sasTeam = new TeamsByConferenceDivision(WESTERN_CONFERENCE.name(), SOUTHWEST_DIVISION.name(), "sas", "San Antonio Spurs");

        TeamsByConferenceDivision bosTeam = new TeamsByConferenceDivision(EASTERN_CONFERENCE.name(), ATLANTIC_DIVISION.name(), "bos", "Boston Celtics");
        TeamsByConferenceDivision chiTeam = new TeamsByConferenceDivision(EASTERN_CONFERENCE.name(), CENTRAL_DIVISION.name(), "chi", "Chicago Bulls");
        TeamsByConferenceDivision miaTeam = new TeamsByConferenceDivision(EASTERN_CONFERENCE.name(), SOUTHEAST_DIVISION.name(), "mia", "Miami Heat");

        teamsByConferenceDivisionRepository.save(gswTeam);
        teamsByConferenceDivisionRepository.save(okcTeam);
        teamsByConferenceDivisionRepository.save(sasTeam);
        teamsByConferenceDivisionRepository.save(bosTeam);
        teamsByConferenceDivisionRepository.save(chiTeam);
        teamsByConferenceDivisionRepository.save(miaTeam);

        List<TeamsByConferenceDivision> westernConferenceTeams = teamsByConferenceDivisionRepository.findTeamsByConference(WESTERN_CONFERENCE.name());
        List<TeamsByConferenceDivision> easternConferenceTeams = teamsByConferenceDivisionRepository.findTeamsByConference(EASTERN_CONFERENCE.name());

        assertEquals(3, westernConferenceTeams.size());
        assertTrue(westernConferenceTeams.contains(gswTeam));
        assertTrue(westernConferenceTeams.contains(okcTeam));
        assertTrue(westernConferenceTeams.contains(sasTeam));

        assertEquals(3, easternConferenceTeams.size());
        assertTrue(easternConferenceTeams.contains(bosTeam));
        assertTrue(easternConferenceTeams.contains(chiTeam));
        assertTrue(easternConferenceTeams.contains(miaTeam));
    }
}
