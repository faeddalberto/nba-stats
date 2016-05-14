package com.faeddalberto.nbastats.model.loader;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.faeddalberto.nbastats.model.configuration.CassandraConfig;
import com.faeddalberto.nbastats.model.domain.Team;
import com.faeddalberto.nbastats.model.domain.TeamRosterByYear;
import com.faeddalberto.nbastats.model.domain.TeamsByConferenceDivision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TeamTablesLoader {

    @Autowired
    private CassandraConfig cassandraConfig;

    public void insertTeamBatch(Team team, TeamsByConferenceDivision teamsByConferenceDivision, List<TeamRosterByYear> teamRosterByYearList) throws Exception {
        Batch batch = QueryBuilder.batch();

        batch.add(teamInsert(team));
        batch.add(teamByConferenceDivisionInsert(teamsByConferenceDivision));

        List<Insert> teamPlayers = teamRosterByYearInsert(teamRosterByYearList);
        teamPlayers.forEach(batch::add);

        cassandraConfig.cassandraTemplate().execute(batch);
    }

    private Insert teamInsert(Team team) {
        List<String> teamColNames = Arrays.asList("team_id", "name", "conference", "division");
        List<Object> teamColValues = Arrays.asList(team.getTeamId(), team.getName(), team.getConference(), team.getDivision());
        return QueryBuilder.insertInto("nba", "team").values(teamColNames, teamColValues);
    }

    private Insert teamByConferenceDivisionInsert(TeamsByConferenceDivision teamByConferenceDivision) {
        List<String> teamByConfDivColNames = Arrays.asList("conference", "division", "team_id", "name");
        List<Object> teamByConfDivColValues = Arrays.asList(teamByConferenceDivision.getConference(), teamByConferenceDivision.getDivision(), teamByConferenceDivision.getTeamId(), teamByConferenceDivision.getName());
        return QueryBuilder.insertInto("nba", "teams_by_conference_division").values(teamByConfDivColNames, teamByConfDivColValues);
    }

    private List<Insert> teamRosterByYearInsert(List<TeamRosterByYear> teamRosterByYearList) {
        List<String> teamRosterColNames = Arrays.asList("year", "team", "player_id", "player_name", "role");

        List<Insert> teamRosterInsert = new ArrayList<>();
        for (TeamRosterByYear teamPlayer :teamRosterByYearList) {
            List<Object> teamRosterColValues = Arrays.asList(teamPlayer.getYear(), teamPlayer.getTeam(), teamPlayer.getPlayerId(), teamPlayer.getPlayerName(), teamPlayer.getRole());
            teamRosterInsert.add(QueryBuilder.insertInto("nba", "team_roster_by_year").values(teamRosterColNames, teamRosterColValues));
        }

        return teamRosterInsert;
    }
}
