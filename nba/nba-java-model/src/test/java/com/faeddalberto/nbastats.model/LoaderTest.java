package com.faeddalberto.nbastats.model;

import com.faeddalberto.nbastats.model.datastaxrepo.*;
import com.faeddalberto.nbastats.model.domain.*;
import com.faeddalberto.nbastats.model.enums.Role;
import com.faeddalberto.nbastats.model.springrepo.*;
import com.google.common.collect.Iterables;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LoaderTest extends CassandraIntegration {

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String PLAYER_TABLE = "player";

    private static final String PLAYER_CAREER_BY_NAME_TABLE = "player_career_by_name";

    private static final String TEAM_TABLE = "team";

    private static final String TEAM_BY_CONF_DIV_TABLE = "teams_by_conference_division";

    private static final String TEAM_ROSTER_TABLE = "team_roster_by_year";

    private static final String GAME_TABLE = "game";

    private static final String GAME_BY_SEASON_TEAMS_TABLE = "game_by_season_teams";

    private static final String PLAYER_STATS_BY_GAME_TABLE = "player_stats_by_game";

    private static final String PLAYER_STATS_BY_OPPONENT = "player_stats_by_opponent";

    private static final String PLAYER_STATS_BY_SEASON = "stats_by_season";

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameBySeasonTeamsRepository gameBySeasonTeamsRepository;

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private PlayerCareerByNameRepository playerCareerByNameRepository;

    @Autowired
    private PlayerStatsByGameRepository playerStatsByGameRepository;

    @Autowired
    private PlayerStatsByOpponentRepository playerStatsByOpponentRepository;

    @Autowired
    private StatsBySeasonRepository statsBySeasonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamsByConferenceDivisionRepository teamsByConferenceDivisionRepository;

    @Autowired
    private TeamRosterByYearRepository teamRosterByYearRepository;

    @Autowired
    private Loader loader;

    @After
    public void dropTable() {
        adminTemplate.truncate(CqlIdentifier.cqlId(PLAYER_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(PLAYER_CAREER_BY_NAME_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(TEAM_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(TEAM_BY_CONF_DIV_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(TEAM_ROSTER_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(GAME_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(GAME_BY_SEASON_TEAMS_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(PLAYER_STATS_BY_GAME_TABLE));
        adminTemplate.truncate(CqlIdentifier.cqlId(PLAYER_STATS_BY_OPPONENT));
        adminTemplate.truncate(CqlIdentifier.cqlId(PLAYER_STATS_BY_SEASON));
    }

    @Test
    public void whenArgumentsArePassedCorrectly_thenTheseAreStoredSuccessfully() {

        String[] args = new String[] {"2001-2002", "2002-2003", "/users/alberto/dev-env"};

        loader.getArguments(args);

        assertEquals(2, loader.getSeasons().size());
        assertEquals("2001-2002", loader.getSeasons().get(0));
        assertEquals("2002-2003",  loader.getSeasons().get(1));
        assertTrue(loader.getRootPath().equals("/users/alberto/dev-env"));
    }

    @Test
    public void whenLoadingSeasonData_thenAvailableOnRetrieval() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        String[] args = new String[] {"2015-2016", classLoader.getResource("seasons").getPath()};
        loader.getArguments(args);
        loader.loadSeasons();

        assertTeamsInfoIsCorrect(teamRepository.findAll());
        assertTeamsByConferenceDivisionInfoIsCorrect(teamsByConferenceDivisionRepository.findAll());
        assertPlayersInfoIsCorrect(playerRepo.getAllPlayers());
        assertPlayerCareerByNameInfoIsCorrect();
        assertTeamRosterByYearIsCorrect();
        assertGamesInfoLoadedCorrectly();
        assertGamesBySeasonTeamsIsLoadedCorrectly();
        assertPlayerStatsByGameInfoAreCorrect();
        assertPlayerStatsByOpponentLoadedCorrectly();
        assertStatsBySeasonLoadedCorrectly();
    }

    private void assertStatsBySeasonLoadedCorrectly() throws Exception {
        List<StatsBySeason> leagueStatsBySeason = statsBySeasonRepository.getLeagueStatsBySeason(2016);
        assertEquals(26, leagueStatsBySeason.size());

        StatsBySeason rJacksonStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Reggie Jackson")).findFirst().get();
        assertEquals("Detroit Pistons", rJacksonStats.getPlayerTeam());
        assertEquals("Atlanta Hawks", rJacksonStats.getOpponentTeam());
        assertEquals(32, rJacksonStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(4,10), rJacksonStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(2,4), rJacksonStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(5,5), rJacksonStats.getFreeThrows());
        assertEquals(1, rJacksonStats.getOffensiveRebounds());
        assertEquals(7, rJacksonStats.getDefensiveRebounds());
        assertEquals(8, rJacksonStats.getTotalRebounds());
        assertEquals(5, rJacksonStats.getAssists());
        assertEquals(2, rJacksonStats.getSteals());
        assertEquals(0, rJacksonStats.getBlocks());
        assertEquals(2, rJacksonStats.getTurnovers());
        assertEquals(0, rJacksonStats.getPersonalFauls());
        assertEquals(26, rJacksonStats.getPlusMinus());
        assertEquals(15, rJacksonStats.getPoints());

        StatsBySeason sJohnsonStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Stanley Johnson")).findFirst().get();
        assertEquals("Detroit Pistons", sJohnsonStats.getPlayerTeam());
        assertEquals("Atlanta Hawks", sJohnsonStats.getOpponentTeam());
        assertEquals(24, sJohnsonStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(3,10), sJohnsonStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(1,3), sJohnsonStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), sJohnsonStats.getFreeThrows());
        assertEquals(3, sJohnsonStats.getOffensiveRebounds());
        assertEquals(1, sJohnsonStats.getDefensiveRebounds());
        assertEquals(4, sJohnsonStats.getTotalRebounds());
        assertEquals(3, sJohnsonStats.getAssists());
        assertEquals(0, sJohnsonStats.getSteals());
        assertEquals(0, sJohnsonStats.getBlocks());
        assertEquals(1, sJohnsonStats.getTurnovers());
        assertEquals(1, sJohnsonStats.getPersonalFauls());
        assertEquals(-13,sJohnsonStats.getPlusMinus());
        assertEquals(7, sJohnsonStats.getPoints());

        StatsBySeason mScottStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Mike Scott")).findFirst().get();
        assertEquals("Atlanta Hawks", mScottStats.getPlayerTeam());
        assertEquals("Detroit Pistons", mScottStats.getOpponentTeam());
        assertEquals(5, mScottStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(2,4), mScottStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(0,2), mScottStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), mScottStats.getFreeThrows());
        assertEquals(0, mScottStats.getOffensiveRebounds());
        assertEquals(1, mScottStats.getDefensiveRebounds());
        assertEquals(1, mScottStats.getTotalRebounds());
        assertEquals(0, mScottStats.getAssists());
        assertEquals(0, mScottStats.getSteals());
        assertEquals(0, mScottStats.getBlocks());
        assertEquals(0, mScottStats.getTurnovers());
        assertEquals(0, mScottStats.getPersonalFauls());
        assertEquals(1, mScottStats.getPlusMinus());
        assertEquals(4, mScottStats.getPoints());

        StatsBySeason dShroderStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Dennis Schroder")).findFirst().get();
        assertEquals("Atlanta Hawks", dShroderStats.getPlayerTeam());
        assertEquals("Detroit Pistons", dShroderStats.getOpponentTeam());
        assertEquals(25, dShroderStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(8,14), dShroderStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(2,5), dShroderStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(2,2), dShroderStats.getFreeThrows());
        assertEquals(1, dShroderStats.getOffensiveRebounds());
        assertEquals(2, dShroderStats.getDefensiveRebounds());
        assertEquals(3, dShroderStats.getTotalRebounds());
        assertEquals(4, dShroderStats.getAssists());
        assertEquals(2, dShroderStats.getSteals());
        assertEquals(0, dShroderStats.getBlocks());
        assertEquals(1, dShroderStats.getTurnovers());
        assertEquals(1, dShroderStats.getPersonalFauls());
        assertEquals(4, dShroderStats.getPlusMinus());
        assertEquals(20, dShroderStats.getPoints());

        StatsBySeason aBaynesStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Aron Baynes")).findFirst().get();
        assertEquals("Detroit Pistons", aBaynesStats.getPlayerTeam());
        assertEquals("Atlanta Hawks", aBaynesStats.getOpponentTeam());
        assertEquals(11, aBaynesStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(3,5), aBaynesStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(0,0), aBaynesStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), aBaynesStats.getFreeThrows());
        assertEquals(1, aBaynesStats.getOffensiveRebounds());
        assertEquals(4, aBaynesStats.getDefensiveRebounds());
        assertEquals(5, aBaynesStats.getTotalRebounds());
        assertEquals(0, aBaynesStats.getAssists());
        assertEquals(0, aBaynesStats.getSteals());
        assertEquals(0, aBaynesStats.getBlocks());
        assertEquals(1, aBaynesStats.getTurnovers());
        assertEquals(2, aBaynesStats.getPersonalFauls());
        assertEquals(-11, aBaynesStats.getPlusMinus());
        assertEquals(6, aBaynesStats.getPoints());

        StatsBySeason kPopeStats = leagueStatsBySeason.stream().filter(stats -> stats.getPlayerName().equals("Kentavious Caldwell-Pope")).findFirst().get();
        assertEquals("Detroit Pistons", kPopeStats.getPlayerTeam());
        assertEquals("Atlanta Hawks", kPopeStats.getOpponentTeam());
        assertEquals(37, kPopeStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(7,14), kPopeStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(4,7), kPopeStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(3,3), kPopeStats.getFreeThrows());
        assertEquals(1, kPopeStats.getOffensiveRebounds());
        assertEquals(3, kPopeStats.getDefensiveRebounds());
        assertEquals(4, kPopeStats.getTotalRebounds());
        assertEquals(1, kPopeStats.getAssists());
        assertEquals(1, kPopeStats.getSteals());
        assertEquals(0, kPopeStats.getBlocks());
        assertEquals(2, kPopeStats.getTurnovers());
        assertEquals(1, kPopeStats.getPersonalFauls());
        assertEquals(17, kPopeStats.getPlusMinus());
        assertEquals(21, kPopeStats.getPoints());
    }

    private void assertPlayerStatsByOpponentLoadedCorrectly() throws Exception {
        PlayerStatsByOpponent drummondStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Atlanta Hawks", "Andre Drummond").get(0);
        assertEquals("Detroit Pistons", drummondStats.getPlayerTeam());
        assertEquals(37, drummondStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(6,16), drummondStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(0,0), drummondStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(6,10), drummondStats.getFreeThrows());
        assertEquals(8, drummondStats.getOffensiveRebounds());
        assertEquals(11, drummondStats.getDefensiveRebounds());
        assertEquals(19, drummondStats.getTotalRebounds());
        assertEquals(3, drummondStats.getAssists());
        assertEquals(1, drummondStats.getSteals());
        assertEquals(2, drummondStats.getBlocks());
        assertEquals(2, drummondStats.getTurnovers());
        assertEquals(4, drummondStats.getPersonalFauls());
        assertEquals(23, drummondStats.getPlusMinus());
        assertEquals(18, drummondStats.getPoints());

        PlayerStatsByOpponent korverStats = playerStatsByOpponentRepository.getPlayerStatsByOpponent("Detroit Pistons", "Kyle Korver").get(0);
        assertEquals("Atlanta Hawks", korverStats.getPlayerTeam());
        assertEquals(29, korverStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(3,9), korverStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(1,5), korverStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), korverStats.getFreeThrows());
        assertEquals(0, korverStats.getOffensiveRebounds());
        assertEquals(2, korverStats.getDefensiveRebounds());
        assertEquals(2, korverStats.getTotalRebounds());
        assertEquals(1, korverStats.getAssists());
        assertEquals(1, korverStats.getSteals());
        assertEquals(0, korverStats.getBlocks());
        assertEquals(1, korverStats.getTurnovers());
        assertEquals(4, korverStats.getPersonalFauls());
        assertEquals(-9, korverStats.getPlusMinus());
        assertEquals(7, korverStats.getPoints());
    }

    private void assertPlayerStatsByGameInfoAreCorrect() throws Exception {
        List<PlayerStatsByGame> allGameStats = playerStatsByGameRepository.getAllGamesStats();
        assertEquals(26, allGameStats.size());

        PlayerStatsByGame sBlakeStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Steve Blake")).findFirst().get();
        assertEquals("PG", sBlakeStats.getPlayerRole());
        assertEquals("Detroit Pistons", sBlakeStats.getPlayerTeam());
        assertEquals(16, sBlakeStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(1,6), sBlakeStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(1,5), sBlakeStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), sBlakeStats.getFreeThrows());
        assertEquals(0, sBlakeStats.getOffensiveRebounds());
        assertEquals(0, sBlakeStats.getDefensiveRebounds());
        assertEquals(0, sBlakeStats.getTotalRebounds());
        assertEquals(4, sBlakeStats.getAssists());
        assertEquals(1, sBlakeStats.getSteals());
        assertEquals(0, sBlakeStats.getBlocks());
        assertEquals(3, sBlakeStats.getTurnovers());
        assertEquals(1, sBlakeStats.getPersonalFauls());
        assertEquals(-14, sBlakeStats.getPlusMinus());
        assertEquals(3, sBlakeStats.getPoints());
        // 400827888,1994,2016,S. Blake,Pistons,PG,16,6-1,5-1,0-0,0,0,0,4,1,0,3,1,-14,3

        PlayerStatsByGame eIlyasovaStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Ersan Ilyasova")).findFirst().get();
        assertEquals("Detroit Pistons", eIlyasovaStats.getPlayerTeam());
        assertEquals(34, eIlyasovaStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(6,12), eIlyasovaStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(3,6), eIlyasovaStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(1,2), eIlyasovaStats.getFreeThrows());
        assertEquals(3, eIlyasovaStats.getOffensiveRebounds());
        assertEquals(4, eIlyasovaStats.getDefensiveRebounds());
        assertEquals(7, eIlyasovaStats.getTotalRebounds());
        assertEquals(3, eIlyasovaStats.getAssists());
        assertEquals(0, eIlyasovaStats.getSteals());
        assertEquals(1, eIlyasovaStats.getBlocks());
        assertEquals(3, eIlyasovaStats.getTurnovers());
        assertEquals(4, eIlyasovaStats.getPersonalFauls());
        assertEquals(20, eIlyasovaStats.getPlusMinus());
        assertEquals(16, eIlyasovaStats.getPoints());
        // 400827888,2767,2016,E. Ilyasova,Pistons,PF,34,12-6,6-3,2-1,3,4,7,3,0,1,3,4,20,16

        PlayerStatsByGame jAnthonyStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Joel Anthony")).findFirst().get();
        assertEquals("Detroit Pistons", jAnthonyStats.getPlayerTeam());
        assertEquals(0, jAnthonyStats.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(0,0), jAnthonyStats.getFieldGoals());
        assertEquals(new MadeAttemptedStat(0,0), jAnthonyStats.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), jAnthonyStats.getFreeThrows());
        assertEquals(0, jAnthonyStats.getOffensiveRebounds());
        assertEquals(0, jAnthonyStats.getDefensiveRebounds());
        assertEquals(0, jAnthonyStats.getTotalRebounds());
        assertEquals(0, jAnthonyStats.getAssists());
        assertEquals(0, jAnthonyStats.getSteals());
        assertEquals(0, jAnthonyStats.getBlocks());
        assertEquals(0, jAnthonyStats.getTurnovers());
        assertEquals(0, jAnthonyStats.getPersonalFauls());
        assertEquals(0, jAnthonyStats.getPlusMinus());
        assertEquals(0, jAnthonyStats.getPoints());
        // 400827888,3247,2016,J. Anthony,Pistons,C,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame jMeeksStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Jodie Meeks")).findFirst().get();
        assertEquals("Detroit Pistons", jMeeksStats.getPlayerTeam());
        assertEquals(11, jMeeksStats.getMinsPlayed());
        // 400827888,4003,2016,J. Meeks,Pistons,SG,11,4-1,0-0,0-0,1,1,2,0,0,0,1,1,-5,2

        PlayerStatsByGame rJacksonStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Reggie Jackson")).findFirst().get();
        assertEquals("Detroit Pistons", rJacksonStats.getPlayerTeam());
        assertEquals(32, rJacksonStats.getMinsPlayed());
        // 400827888,6443,2016,R. Jackson,Pistons,PG,32,10-4,4-2,5-5,1,7,8,5,2,0,2,0,26,15

        PlayerStatsByGame mMorrisStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Marcus Morris")).findFirst().get();
        assertEquals("Detroit Pistons", mMorrisStats.getPlayerTeam());
        assertEquals(37, mMorrisStats.getMinsPlayed());
        //400827888,6462,2016,M. Morris,Pistons,PF,37,19-6,4-1,6-5,5,5,10,4,0,0,0,1,17,18

        PlayerStatsByGame aDrummondStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Andre Drummond")).findFirst().get();
        assertEquals("Detroit Pistons", aDrummondStats.getPlayerTeam());
        assertEquals(37, aDrummondStats.getMinsPlayed());
        //400827888,6585,2016,A. Drummond,Pistons,C,37,16-6,0-0,10-6,8,11,19,3,1,2,2,4,23,18

        PlayerStatsByGame rBullockStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Reggie Bullock")).findFirst().get();
        assertEquals("Detroit Pistons", rBullockStats.getPlayerTeam());
        assertEquals(0, rBullockStats.getMinsPlayed());
        //400827888,2528779,2016,R. Bullock,Pistons,SF,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame dHilliardStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Darrun Hilliard")).findFirst().get();
        assertEquals("Detroit Pistons", dHilliardStats.getPlayerTeam());
        assertEquals(0, dHilliardStats.getMinsPlayed());
        //400827888,2578259,2016,D. Hilliard,Pistons,SF,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame sDinwiddieStats = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Spencer Dinwiddie")).findFirst().get();
        assertEquals("Detroit Pistons", sDinwiddieStats.getPlayerTeam());
        assertEquals(0, sDinwiddieStats.getMinsPlayed());
        //400827888,2580782,2016,S. Dinwiddie,Pistons,PG,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame kPope = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Kentavious Caldwell-Pope")).findFirst().get();
        assertEquals("Detroit Pistons", kPope.getPlayerTeam());
        assertEquals(37, kPope.getMinsPlayed());
        //400827888,2581018,2016,K. Caldwell-Pope,Pistons,SG,37,14-7,7-4,3-3,1,3,4,1,1,0,2,1,17,21

        PlayerStatsByGame aBaynes = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Aron Baynes")).findFirst().get();
        assertEquals("Detroit Pistons", aBaynes.getPlayerTeam());
        assertEquals(11, aBaynes.getMinsPlayed());
        //400827888,2968439,2016,A. Baynes,Pistons,C,11,5-3,0-0,0-0,1,4,5,0,0,0,1,2,-11,6

        PlayerStatsByGame sJohnson = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Stanley Johnson")).findFirst().get();
        assertEquals("Detroit Pistons", sJohnson.getPlayerTeam());
        assertEquals(24, sJohnson.getMinsPlayed());
        //400827888,3134881,2016,S. Johnson,Pistons,SF,24,10-3,3-1,0-0,3,1,4,3,0,0,1,1,-13,7


        PlayerStatsByGame kKorver = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Kyle Korver")).findFirst().get();
        assertEquals("Atlanta Hawks", kKorver.getPlayerTeam());
        assertEquals(29, kKorver.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(3,9), kKorver.getFieldGoals());
        assertEquals(new MadeAttemptedStat(1,5), kKorver.getThreePoints());
        assertEquals(new MadeAttemptedStat(0,0), kKorver.getFreeThrows());
        assertEquals(0, kKorver.getOffensiveRebounds());
        assertEquals(2, kKorver.getDefensiveRebounds());
        assertEquals(2, kKorver.getTotalRebounds());
        assertEquals(1, kKorver.getAssists());
        assertEquals(1, kKorver.getSteals());
        assertEquals(0, kKorver.getBlocks());
        assertEquals(1, kKorver.getTurnovers());
        assertEquals(4, kKorver.getPersonalFauls());
        assertEquals(-9, kKorver.getPlusMinus());
        assertEquals(7, kKorver.getPoints());
        //400827888,2011,2016,K. Korver,Hawks,SG,29,9-3,5-1,0-0,0,2,2,1,1,0,1,4,-9,7

        PlayerStatsByGame pMillsap = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Paul Millsap")).findFirst().get();
        assertEquals("Atlanta Hawks", pMillsap.getPlayerTeam());
        assertEquals(36, pMillsap.getMinsPlayed());
        assertEquals(new MadeAttemptedStat(7,15), pMillsap.getFieldGoals());
        assertEquals(new MadeAttemptedStat(2,6), pMillsap.getThreePoints());
        assertEquals(new MadeAttemptedStat(3,4), pMillsap.getFreeThrows());
        assertEquals(1, pMillsap.getOffensiveRebounds());
        assertEquals(7, pMillsap.getDefensiveRebounds());
        assertEquals(8, pMillsap.getTotalRebounds());
        assertEquals(3, pMillsap.getAssists());
        assertEquals(0, pMillsap.getSteals());
        assertEquals(0, pMillsap.getBlocks());
        assertEquals(2, pMillsap.getTurnovers());
        assertEquals(4, pMillsap.getPersonalFauls());
        assertEquals(-22, pMillsap.getPlusMinus());
        assertEquals(19, pMillsap.getPoints());
        // 400827888,3015,2016,P. Millsap,Hawks,PF,36,15-7,6-2,4-3,1,7,8,3,0,0,2,4,-22,19

        PlayerStatsByGame tSefolosha = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Thabo Sefolosha")).findFirst().get();
        assertEquals("Atlanta Hawks", tSefolosha.getPlayerTeam());
        assertEquals(19, tSefolosha.getMinsPlayed());
        //400827888,3028,2016,T. Sefolosha,Hawks,SF,19,3-1,1-0,0-0,1,6,7,3,4,0,0,1,-1,2

        PlayerStatsByGame aHorford = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Al Horford")).findFirst().get();
        assertEquals("Atlanta Hawks", aHorford.getPlayerTeam());
        assertEquals(30, aHorford.getMinsPlayed());
        //400827888,3213,2016,A. Horford,Hawks,C,30,11-6,3-1,3-2,1,3,4,4,2,3,1,1,-5,15

        PlayerStatsByGame tSplitter = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Tiago Splitter")).findFirst().get();
        assertEquals("Atlanta Hawks", tSplitter.getPlayerTeam());
        assertEquals(16, tSplitter.getMinsPlayed());
        //400827888,3233,2016,T. Splitter,Hawks,C,16,5-2,0-0,0-0,3,1,4,0,0,1,0,4,-3,4

        PlayerStatsByGame jTeague = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Jeff Teague")).findFirst().get();
        assertEquals("Atlanta Hawks", jTeague.getPlayerTeam());
        assertEquals(32, jTeague.getMinsPlayed());
        //400827888,4015,2016,J. Teague,Hawks,PG,32,16-7,3-1,4-3,0,2,2,4,0,0,5,1,-23,18

        PlayerStatsByGame sMack = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Shelvin Mack")).findFirst().get();
        assertEquals("Atlanta Hawks", sMack.getPlayerTeam());
        assertEquals(0, sMack.getMinsPlayed());
        //400827888,6454,2016,S. Mack,Hawks,PG,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame mScott = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Mike Scott")).findFirst().get();
        assertEquals("Atlanta Hawks", mScott.getPlayerTeam());
        assertEquals(5, mScott.getMinsPlayed());
        // 400827888,6622,2016,M. Scott,Hawks,PF,5,4-2,2-0,0-0,0,1,1,0,0,0,0,0,1,4

        PlayerStatsByGame kBazemore = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Kent Bazemore")).findFirst().get();
        assertEquals("Atlanta Hawks", kBazemore.getPlayerTeam());
        assertEquals(21, kBazemore.getMinsPlayed());
        //400827888,6637,2016,K. Bazemore,Hawks,SF,21,3-0,1-0,0-0,0,7,7,1,0,0,4,3,-17,0

        PlayerStatsByGame jHoliday = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Justin Holiday")).findFirst().get();
        assertEquals("Atlanta Hawks", jHoliday.getPlayerTeam());
        assertEquals(0, jHoliday.getMinsPlayed());
        //400827888,2284101,2016,J. Holiday,Hawks,SG,0,0-0,0-0,0-0,0,0,0,0,0,0,0,0,0,0

        PlayerStatsByGame lPatterson = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Lamar Patterson")).findFirst().get();
        assertEquals("Atlanta Hawks", lPatterson.getPlayerTeam());
        assertEquals(18, lPatterson.getMinsPlayed());
        //400827888,2488721,2016,L. Patterson,Hawks,SG,18,1-1,1-1,2-2,0,1,1,2,0,0,0,4,10,5

        PlayerStatsByGame mMuscala = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Mike Muscala")).findFirst().get();
        assertEquals("Atlanta Hawks", mMuscala.getPlayerTeam());
        assertEquals(8, mMuscala.getMinsPlayed());
        //400827888,2490089,2016,M. Muscala,Hawks,PF,8,1-0,0-0,0-0,0,1,1,0,0,0,1,2,5,0

        PlayerStatsByGame dSchroder = allGameStats.stream().filter(playerStats -> playerStats.getPlayerName().equals("Dennis Schroder")).findFirst().get();
        assertEquals("Atlanta Hawks", dSchroder.getPlayerTeam());
        assertEquals(25, dSchroder.getMinsPlayed());
        //400827888,3032979,2016,D. Schroder,Hawks,PG,25,14-8,5-2,2-2,1,2,3,4,2,0,1,1,4,20
    }

    private void assertGamesBySeasonTeamsIsLoadedCorrectly() throws ParseException {
        GameBySeasonTeams gameBySeasonTeamsDate1 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Detroit Pistons", "Atlanta Hawks", formatter.parse("2015-10-27")).get(0);
        assertNotNull(gameBySeasonTeamsDate1);
        assertEquals(94, gameBySeasonTeamsDate1.getHomeTeamScore());
        assertEquals(106, gameBySeasonTeamsDate1.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate2 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "New Orleans Pelicans", "Atlanta Hawks", formatter.parse("2015-11-06")).get(0);
        assertNotNull(gameBySeasonTeamsDate2);
        assertEquals(115, gameBySeasonTeamsDate2.getHomeTeamScore());
        assertEquals(121, gameBySeasonTeamsDate2.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate3 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Atlanta Hawks", "Sacramento Kings", formatter.parse("2015-11-18")).get(0);
        assertNotNull(gameBySeasonTeamsDate3);
        assertEquals(103, gameBySeasonTeamsDate3.getHomeTeamScore());
        assertEquals(97, gameBySeasonTeamsDate3.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate4 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Atlanta Hawks", "Oklahoma City Thunder", formatter.parse("2015-11-30")).get(0);
        assertNotNull(gameBySeasonTeamsDate4);
        assertEquals(106, gameBySeasonTeamsDate4.getHomeTeamScore());
        assertEquals(100, gameBySeasonTeamsDate4.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate5 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Atlanta Hawks", "Philadelphia 76ers", formatter.parse("2015-12-16")).get(0);
        assertNotNull(gameBySeasonTeamsDate5);
        assertEquals(127, gameBySeasonTeamsDate5.getHomeTeamScore());
        assertEquals(106, gameBySeasonTeamsDate5.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate6 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Houston Rockets", "Atlanta Hawks", formatter.parse("2015-12-29")).get(0);
        assertNotNull(gameBySeasonTeamsDate6);
        assertEquals(115, gameBySeasonTeamsDate6.getHomeTeamScore());
        assertEquals(121, gameBySeasonTeamsDate6.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate7 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Portland Trail Blazers", "Atlanta Hawks", formatter.parse("2016-01-20")).get(0);
        assertNotNull(gameBySeasonTeamsDate7);
        assertEquals(98, gameBySeasonTeamsDate7.getHomeTeamScore());
        assertEquals(104, gameBySeasonTeamsDate7.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate8 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Atlanta Hawks", "Indiana Pacers", formatter.parse("2016-02-05")).get(0);
        assertNotNull(gameBySeasonTeamsDate8);
        assertEquals(102, gameBySeasonTeamsDate8.getHomeTeamScore());
        assertEquals(96, gameBySeasonTeamsDate8.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate9 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Los Angeles Clippers", "Atlanta Hawks", formatter.parse("2016-03-04")).get(0);
        assertNotNull(gameBySeasonTeamsDate9);
        assertEquals(77, gameBySeasonTeamsDate9.getHomeTeamScore());
        assertEquals(106, gameBySeasonTeamsDate9.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate0 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Washington Wizards", "Atlanta Hawks", formatter.parse("2016-03-21")).get(0);
        assertNotNull(gameBySeasonTeamsDate0);
        assertEquals(102, gameBySeasonTeamsDate0.getHomeTeamScore());
        assertEquals(117, gameBySeasonTeamsDate0.getVisitorTeamScore());

        GameBySeasonTeams gameBySeasonTeamsDate01 =
                gameBySeasonTeamsRepository.findBySeasonHomeTeamVisitorTeamDate(2016, "Atlanta Hawks", "Washington Wizards", formatter.parse("2016-04-13")).get(0);
        assertNotNull(gameBySeasonTeamsDate01);
        assertEquals(109, gameBySeasonTeamsDate01.getHomeTeamScore());
        assertEquals(98, gameBySeasonTeamsDate01.getVisitorTeamScore());
    }

    private void assertGamesInfoLoadedCorrectly() {
        Iterable<Game> allGames = gameRepository.findAll();
        assertEquals(82, Iterables.size(allGames));

        for (Game game :allGames) {
            assertTrue(game.getGameId() != null && game.getGameId() instanceof UUID);
            assertEquals(2016, game.getSeason());
            assertEquals("REGULAR_SEASON", game.getSeasonType());

            switch(formatter.format(game.getDate())) {
                case "2015-10-27":
                        assertEquals("Detroit Pistons", game.getHomeTeam());
                        assertEquals(94, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(106, game.getVisitorTeamScore());
                    break;
                case "2015-11-01":
                        assertEquals("Charlotte Hornets", game.getHomeTeam());
                        assertEquals(92, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(94, game.getVisitorTeamScore());
                    break;
                case "2015-11-11":
                        assertEquals("Atlanta Hawks", game.getHomeTeam());
                        assertEquals(106, game.getHomeTeamScore());
                        assertEquals("New Orleans Pelicans", game.getVisitorTeam());
                        assertEquals(98, game.getVisitorTeamScore());
                    break;
                case "2015-11-25":
                        assertEquals("Atlanta Hawks", game.getHomeTeam());
                        assertEquals(99, game.getHomeTeamScore());
                        assertEquals("Minnesota Timberwolves", game.getVisitorTeam());
                        assertEquals(95, game.getVisitorTeamScore());
                    break;
                case "2015-12-12":
                        assertEquals("San Antonio Spurs", game.getHomeTeam());
                        assertEquals(78, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(103, game.getVisitorTeamScore());
                    break;
                case "2016-01-05":
                        assertEquals("New York Knicks", game.getHomeTeam());
                        assertEquals(101, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(107, game.getVisitorTeamScore());
                    break;
                case "2016-01-20":
                        assertEquals("Portland Trail Blazers", game.getHomeTeam());
                        assertEquals(98, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(104, game.getVisitorTeamScore());
                    break;
                case "2016-02-20":
                        assertEquals("Milwaukee Bucks", game.getHomeTeam());
                        assertEquals(109, game.getHomeTeamScore());
                        assertEquals("Atlanta Hawks", game.getVisitorTeam());
                        assertEquals(117, game.getVisitorTeamScore());
                    break;
                case "2016-03-19":
                        assertEquals("Atlanta Hawks", game.getHomeTeam());
                        assertEquals(109, game.getHomeTeamScore());
                        assertEquals("Houston Rockets", game.getVisitorTeam());
                        assertEquals(97, game.getVisitorTeamScore());
                    break;
                case "2016-04-07":
                        assertEquals("Atlanta Hawks", game.getHomeTeam());
                        assertEquals(95, game.getHomeTeamScore());
                        assertEquals("Toronto Raptors", game.getVisitorTeam());
                        assertEquals(87, game.getVisitorTeamScore());
                    break;
                case "2016-04-13":
                        assertEquals("Atlanta Hawks", game.getHomeTeam());
                        assertEquals(109, game.getHomeTeamScore());
                        assertEquals("Washington Wizards", game.getVisitorTeam());
                        assertEquals(98, game.getVisitorTeamScore());
                    break;
            }
        }
    }

    private void assertTeamRosterByYearIsCorrect() {
        List<TeamRosterByYearPlayer> pistons2016 = teamRosterByYearRepository.findTeamRosterByYear(2016, "Detroit Pistons");
        assertEquals(13, pistons2016.size());
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Steve Blake") && player.getRole().equals("PG")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Ersan Ilyasova") && player.getRole().equals("PF")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Joel Anthony") && player.getRole().equals("C")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Jodie Meeks") && player.getRole().equals("SG")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Reggie Jackson") && player.getRole().equals("PG")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Marcus Morris") && player.getRole().equals("PF")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Andre Drummond") && player.getRole().equals("C")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Reggie Bullock") && player.getRole().equals("SF")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Darrun Hilliard") && player.getRole().equals("SF")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Spencer Dinwiddie") && player.getRole().equals("PG")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Kentavious Caldwell-Pope") && player.getRole().equals("SG")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Aron Baynes") && player.getRole().equals("C")));
        assertTrue(pistons2016.stream().anyMatch(player -> player.getPlayerName().equals("Stanley Johnson") && player.getRole().equals("SF")));

        List<TeamRosterByYearPlayer> hawks2016 = teamRosterByYearRepository.findTeamRosterByYear(2016, "Atlanta Hawks");
        assertEquals(13, hawks2016.size());
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Kyle Korver") && player.getRole().equals("SG")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Paul Millsap") && player.getRole().equals("PF")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Thabo Sefolosha") && player.getRole().equals("SF")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Al Horford") && player.getRole().equals("C")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Tiago Splitter") && player.getRole().equals("C")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Jeff Teague") && player.getRole().equals("PG")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Shelvin Mack") && player.getRole().equals("PG")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Mike Scott") && player.getRole().equals("PF")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Kent Bazemore") && player.getRole().equals("SF")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Justin Holiday") && player.getRole().equals("SG")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Lamar Patterson") && player.getRole().equals("SG")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Mike Muscala") && player.getRole().equals("PF")));
        assertTrue(hawks2016.stream().anyMatch(player -> player.getPlayerName().equals("Dennis Schroder") && player.getRole().equals("PG")));

    }

    private void assertPlayerCareerByNameInfoIsCorrect() throws Exception {
        List<PlayerCareerByName> sBlakeCareer = playerCareerByNameRepository.getPlayerCareerByName("Steve Blake");
        List<PlayerCareerByName> kKorverCareer = playerCareerByNameRepository.getPlayerCareerByName("Kyle Korver");
        List<PlayerCareerByName> eIlyasovaCareer = playerCareerByNameRepository.getPlayerCareerByName("Ersan Ilyasova");
        List<PlayerCareerByName> pMillsapCareer = playerCareerByNameRepository.getPlayerCareerByName("Paul Millsap");
        List<PlayerCareerByName> tSefoloshaCareer = playerCareerByNameRepository.getPlayerCareerByName("Thabo Sefolosha");
        List<PlayerCareerByName> aHorford = playerCareerByNameRepository.getPlayerCareerByName("Al Horford");
        List<PlayerCareerByName> tSplitterCareer = playerCareerByNameRepository.getPlayerCareerByName("Tiago Splitter");
        List<PlayerCareerByName> jAnthonyCareer = playerCareerByNameRepository.getPlayerCareerByName("Joel Anthony");
        List<PlayerCareerByName> jMeeksCareer = playerCareerByNameRepository.getPlayerCareerByName("Jodie Meeks");
        List<PlayerCareerByName> jTeagueCareer = playerCareerByNameRepository.getPlayerCareerByName("Jeff Teague");
        List<PlayerCareerByName> rJacksonCareer = playerCareerByNameRepository.getPlayerCareerByName("Reggie Jackson");
        List<PlayerCareerByName> sMackCareer = playerCareerByNameRepository.getPlayerCareerByName("Shelvin Mack");
        List<PlayerCareerByName> mMorrisCareer = playerCareerByNameRepository.getPlayerCareerByName("Marcus Morris");
        List<PlayerCareerByName> aDrummondCareer = playerCareerByNameRepository.getPlayerCareerByName("Andre Drummond");
        List<PlayerCareerByName> mScottCareer = playerCareerByNameRepository.getPlayerCareerByName("Mike Scott");
        List<PlayerCareerByName> kBazemoreCareer = playerCareerByNameRepository.getPlayerCareerByName("Kent Bazemore");
        List<PlayerCareerByName> jHolidayCareer = playerCareerByNameRepository.getPlayerCareerByName("Justin Holiday");
        List<PlayerCareerByName> lPattersonCareer = playerCareerByNameRepository.getPlayerCareerByName("Lamar Patterson");
        List<PlayerCareerByName> mMuscalaCareer = playerCareerByNameRepository.getPlayerCareerByName("Mike Muscala");
        List<PlayerCareerByName> rBullockCareer = playerCareerByNameRepository.getPlayerCareerByName("Reggie Bullock");
        List<PlayerCareerByName> dHilliardCareer = playerCareerByNameRepository.getPlayerCareerByName("Darrun Hilliard");
        List<PlayerCareerByName> sDinwiddieCareer = playerCareerByNameRepository.getPlayerCareerByName("Spencer Dinwiddie");
        List<PlayerCareerByName> kcPopeCareer = playerCareerByNameRepository.getPlayerCareerByName("Kentavious Caldwell-Pope");
        List<PlayerCareerByName> aBaynesCareer = playerCareerByNameRepository.getPlayerCareerByName("Aron Baynes");
        List<PlayerCareerByName> dShroderCareer = playerCareerByNameRepository.getPlayerCareerByName("Dennis Schroder");
        List<PlayerCareerByName> sJohnsonCareer = playerCareerByNameRepository.getPlayerCareerByName("Stanley Johnson");

        only2016YearCareer(mScottCareer);
        assertHawksPlayer(mScottCareer.get(0));
        assertTrue(mScottCareer.get(0).getRole() == Role.POWER_FORWARD);

        only2016YearCareer(sBlakeCareer);
        assertPistonsPlayer(sBlakeCareer.get(0));
        assertTrue(sBlakeCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(kKorverCareer);
        assertHawksPlayer(kKorverCareer.get(0));
        assertTrue(kKorverCareer.get(0).getRole() == Role.SHOOTING_GUARD);

        only2016YearCareer(eIlyasovaCareer);
        assertTrue(eIlyasovaCareer.get(0).getRole() == Role.POWER_FORWARD);
        assertPistonsPlayer(eIlyasovaCareer.get(0));

        only2016YearCareer(pMillsapCareer);
        assertTrue(pMillsapCareer.get(0).getRole() == Role.POWER_FORWARD);
        assertHawksPlayer(pMillsapCareer.get(0));

        only2016YearCareer(tSefoloshaCareer);
        assertHawksPlayer(tSefoloshaCareer.get(0));
        assertTrue(tSefoloshaCareer.get(0).getRole() == Role.SMALL_FORWARD);

        only2016YearCareer(aHorford);
        assertHawksPlayer(aHorford.get(0));
        assertTrue(aHorford.get(0).getRole() == Role.CENTER);

        only2016YearCareer(tSplitterCareer);
        assertHawksPlayer(tSplitterCareer.get(0));
        assertTrue(tSplitterCareer.get(0).getRole() == Role.CENTER);

        only2016YearCareer(jAnthonyCareer);
        assertPistonsPlayer(jAnthonyCareer.get(0));
        assertTrue(jAnthonyCareer.get(0).getRole() == Role.CENTER);

        only2016YearCareer(jMeeksCareer);
        assertPistonsPlayer(jMeeksCareer.get(0));
        assertTrue(jMeeksCareer.get(0).getRole() == Role.SHOOTING_GUARD);

        only2016YearCareer(jTeagueCareer);
        assertHawksPlayer(jTeagueCareer.get(0));
        assertTrue(jTeagueCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(rJacksonCareer);
        assertPistonsPlayer(rJacksonCareer.get(0));
        assertTrue(rJacksonCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(sMackCareer);
        assertHawksPlayer(sMackCareer.get(0));
        assertTrue(sMackCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(mMorrisCareer);
        assertTrue(mMorrisCareer.get(0).getRole() == Role.POWER_FORWARD);
        assertPistonsPlayer(mMorrisCareer.get(0));

        only2016YearCareer(aDrummondCareer);
        assertPistonsPlayer(aDrummondCareer.get(0));
        assertTrue(aDrummondCareer.get(0).getRole() == Role.CENTER);

        only2016YearCareer(kBazemoreCareer);
        assertHawksPlayer(kBazemoreCareer.get(0));
        assertTrue(kBazemoreCareer.get(0).getRole() == Role.SMALL_FORWARD);

        only2016YearCareer(jHolidayCareer);
        assertHawksPlayer(jHolidayCareer.get(0));
        assertTrue(jHolidayCareer.get(0).getRole() == Role.SHOOTING_GUARD);

        only2016YearCareer(lPattersonCareer);
        assertHawksPlayer(lPattersonCareer.get(0));
        assertTrue(lPattersonCareer.get(0).getRole() == Role.SHOOTING_GUARD);

        only2016YearCareer(mMuscalaCareer);
        assertHawksPlayer(mMuscalaCareer.get(0));
        assertTrue(mMuscalaCareer.get(0).getRole() == Role.POWER_FORWARD);

        only2016YearCareer(rBullockCareer);
        assertPistonsPlayer(rBullockCareer.get(0));
        assertTrue(rBullockCareer.get(0).getRole() == Role.SMALL_FORWARD);

        only2016YearCareer(dHilliardCareer);
        assertPistonsPlayer(dHilliardCareer.get(0));
        assertTrue(dHilliardCareer.get(0).getRole() == Role.SMALL_FORWARD);

        only2016YearCareer(sDinwiddieCareer);
        assertPistonsPlayer(sDinwiddieCareer.get(0));
        assertTrue(sDinwiddieCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(kcPopeCareer);
        assertPistonsPlayer(kcPopeCareer.get(0));
        assertTrue(kcPopeCareer.get(0).getRole() == Role.SHOOTING_GUARD);

        only2016YearCareer(aBaynesCareer);
        assertPistonsPlayer(aBaynesCareer.get(0));
        assertTrue(aBaynesCareer.get(0).getRole() == Role.CENTER);

        only2016YearCareer(dShroderCareer);
        assertHawksPlayer(dShroderCareer.get(0));
        assertTrue(dShroderCareer.get(0).getRole() == Role.POINT_GUARD);

        only2016YearCareer(sJohnsonCareer);
        assertPistonsPlayer(sJohnsonCareer.get(0));
        assertTrue(sJohnsonCareer.get(0).getRole() == Role.SMALL_FORWARD);
    }

    private void only2016YearCareer(List<PlayerCareerByName> playerCareerByName) {
        assertTrue(playerCareerByName.size() == 1);
        assertTrue(playerCareerByName.get(0).getYear() == 2016);
        assertTrue(playerCareerByName.get(0).getPlayerId() != null && playerCareerByName.get(0).getPlayerId() instanceof UUID);
    }

    private void assertPistonsPlayer(PlayerCareerByName playerCareerByName) {
       assertTrue(playerCareerByName.getTeam().equals("Detroit Pistons"));
    }

    private void assertHawksPlayer(PlayerCareerByName playerCareerByName) {
        assertTrue(playerCareerByName.getTeam().equals("Atlanta Hawks"));
    }

    private void assertPlayersInfoIsCorrect(List<Player> allPlayers) throws ParseException {

        int totalPlayers = 0;
        for (Player player: allPlayers) {
            totalPlayers++;
            assertTrue(player.getPlayerId() != null && player.getPlayerId() instanceof UUID);

            switch (player.getName()) {
                case "Steve Blake":
                    assertEquals(formatter.parse("1980-02-26"), player.getDateOfBirth());
                    assertEquals("Hollywood - FL", player.getCountry());
                    assertEquals("2003: 2nd Rnd 38th by WSH", player.getDraftedInfo());
                    break;
                case "Kyle Korver":
                    assertEquals(formatter.parse("1981-03-17") , player.getDateOfBirth());
                    assertEquals("Lakewood - CA", player.getCountry());
                    assertEquals("2003: 2nd Rnd 51st by NJ", player.getDraftedInfo());
                    break;
                case "Ersan Ilyasova":
                    assertEquals(formatter.parse("1987-05-15"), player.getDateOfBirth());
                    assertEquals("Turkey", player.getCountry());
                    assertEquals("2005: 2nd Rnd 36th by MIL", player.getDraftedInfo());
                    break;
                case "Paul Millsap":
                    assertEquals(formatter.parse("1985-02-10") , player.getDateOfBirth());
                    assertEquals("Monroe - LA", player.getCountry());
                    assertEquals("2006: 2nd Rnd 47th by UTAH", player.getDraftedInfo());
                    break;
                case "Thabo Sefolosha":
                    assertEquals(formatter.parse("1984-05-02") , player.getDateOfBirth());
                    assertEquals("Switzerland", player.getCountry());
                    assertEquals("2006: 1st Rnd 13th by PHI", player.getDraftedInfo());
                    break;
                case "Al Horford":
                    assertEquals(formatter.parse("1986-06-03") , player.getDateOfBirth());
                    assertEquals("Dom", player.getCountry());
                    assertEquals("2007: 1st Rnd 3rd by ATL", player.getDraftedInfo());
                    break;
                case "Tiago Splitter":
                    assertEquals(formatter.parse("1985-01-01") , player.getDateOfBirth());
                    assertEquals("Brazil", player.getCountry());
                    assertEquals("2007: 1st Rnd 28th by SA", player.getDraftedInfo());
                    break;
                case "Joel Anthony":
                    assertEquals(formatter.parse("1982-08-09") , player.getDateOfBirth());
                    assertEquals("Canada", player.getCountry());
                    assertEquals("n.a.", player.getDraftedInfo());
                    break;
                case "Jodie Meeks":
                    assertEquals(formatter.parse("1987-08-21") , player.getDateOfBirth());
                    assertEquals("Nashville - TN", player.getCountry());
                    assertEquals("2009: 2nd Rnd 41st by MIL", player.getDraftedInfo());
                    break;
                case "Jeff Teague":
                    assertEquals(formatter.parse("1988-06-10") , player.getDateOfBirth());
                    assertEquals("Indianapolis - IN", player.getCountry());
                    assertEquals("2009: 1st Rnd 19th by ATL", player.getDraftedInfo());
                    break;
                case "Reggie Jackson":
                    assertEquals(formatter.parse("1990-04-16") , player.getDateOfBirth());
                    assertEquals("Italy", player.getCountry());
                    assertEquals("2011: 1st Rnd 24th by OKC", player.getDraftedInfo());
                    break;
                case "Shelvin Mack":
                    assertEquals(formatter.parse("1990-04-22") , player.getDateOfBirth());
                    assertEquals("Lex", player.getCountry());
                    assertEquals("2011: 2nd Rnd 34th by WSH", player.getDraftedInfo());
                    break;
                case "Marcus Morris":
                    assertEquals(formatter.parse("1989-09-02") , player.getDateOfBirth());
                    assertEquals("Philadelphia - PA", player.getCountry());
                    assertEquals("2011: 1st Rnd 14th by HOU", player.getDraftedInfo());
                    break;
                case "Andre Drummond":
                    assertEquals(formatter.parse("1993-08-10") , player.getDateOfBirth());
                    assertEquals("Mount Vernon - NY", player.getCountry());
                    assertEquals("2012: 1st Rnd 9th by DET", player.getDraftedInfo());
                    break;
                case "Mike Scott":
                    assertEquals(formatter.parse("1988-07-16") , player.getDateOfBirth());
                    assertEquals("Chesapeake - VA", player.getCountry());
                    assertEquals("2012: 2nd Rnd 43rd by ATL", player.getDraftedInfo());
                    break;
                case "Kent Bazemore":
                    assertEquals(formatter.parse("1989-07-01") , player.getDateOfBirth());
                    assertEquals("Kelford - NC", player.getCountry());
                    assertEquals("n.a.", player.getDraftedInfo());
                    break;
                case "Justin Holiday":
                    assertEquals(formatter.parse("1989-04-05") , player.getDateOfBirth());
                    assertEquals("Mission Hills - CA", player.getCountry());
                    assertEquals("n.a.", player.getDraftedInfo());
                    break;
                case "Lamar Patterson":
                    assertEquals(formatter.parse("1991-08-12") , player.getDateOfBirth());
                    assertEquals("Lancaster - PA", player.getCountry());
                    assertEquals("2014: 2nd Rnd 48th by MIL", player.getDraftedInfo());
                    break;
                case "Mike Muscala":
                    assertEquals(formatter.parse("1991-07-01") , player.getDateOfBirth());
                    assertEquals("n.a.", player.getCountry());
                    assertEquals("2013: 2nd Rnd 44th by DAL", player.getDraftedInfo());
                    break;
                case "Reggie Bullock":
                    assertEquals(formatter.parse("1991-03-16") , player.getDateOfBirth());
                    assertEquals("Baltimore - MD", player.getCountry());
                    assertEquals("2013: 1st Rnd 25th by LAC", player.getDraftedInfo());
                    break;
                case "Darrun Hilliard":
                    assertEquals(formatter.parse("1993-04-13") , player.getDateOfBirth());
                    assertEquals("Bethlehem - PA", player.getCountry());
                    assertEquals("2015: 2nd Rnd 38th by DET", player.getDraftedInfo());
                    break;
                case "Spencer Dinwiddie":
                    assertEquals(formatter.parse("1993-04-06") , player.getDateOfBirth());
                    assertEquals("Los Angeles - CA", player.getCountry());
                    assertEquals("2014: 2nd Rnd 38th by DET", player.getDraftedInfo());
                    break;
                case "Kentavious Caldwell - Pope":
                    assertEquals(formatter.parse("1993-02-18") , player.getDateOfBirth());
                    assertEquals("Thomaston - GA", player.getCountry());
                    assertEquals("2013: 1st Rnd 8th by DET", player.getDraftedInfo());
                    break;
                case "Aron Baynes":
                    assertEquals(formatter.parse("1986-12-09") , player.getDateOfBirth());
                    assertEquals("New Zealand", player.getCountry());
                    assertEquals("n.a.", player.getDraftedInfo());
                    break;
                case "Dennis Schroder":
                    assertEquals(formatter.parse("1993-09-15") , player.getDateOfBirth());
                    assertEquals("Germany", player.getCountry());
                    assertEquals("2013: 1st Rnd 17th by ATL", player.getDraftedInfo());
                    break;
                case "Stanley Johnson":
                    assertEquals(formatter.parse("1996-05-29") , player.getDateOfBirth());
                    assertEquals("Anaheim - CA", player.getCountry());
                    assertEquals("2015: 1st Rnd 8th by DET", player.getDraftedInfo());
                    break;
            }
        }
        assertEquals(26, totalPlayers);
    }

    private void assertTeamsByConferenceDivisionInfoIsCorrect(Iterable<TeamsByConferenceDivision> allTeamsByConfDiv) {
        Iterator<TeamsByConferenceDivision> teamsByConfDivIterator = allTeamsByConfDiv.iterator();

        int totalTeams = 0;
        while (teamsByConfDivIterator.hasNext()) {
            TeamsByConferenceDivision team = teamsByConfDivIterator.next();
            totalTeams++;
            switch(team.getTeamId()) {

                case "bos":
                    assertEquals("Boston Celtics", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "bkn":
                    assertEquals("Brooklyn Nets", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "nyk":
                    assertEquals("New York Knicks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "phi":
                    assertEquals("Philadelphia 76ers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "tor":
                    assertEquals("Toronto Raptors", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "gsw":
                    assertEquals("Golden State Warriors", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "lac":
                    assertEquals("Los Angeles Clippers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "lal":
                    assertEquals("Los Angeles Lakers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "phx":
                    assertEquals("Phoenix Suns", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "sac":
                    assertEquals("Sacramento Kings", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "chi":
                    assertEquals("Chicago Bulls", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "cle":
                    assertEquals("Cleveland Cavaliers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "det":
                    assertEquals("Detroit Pistons", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "ind":
                    assertEquals("Indiana Pacers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "uth":
                    assertEquals("Utah Jazz", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "por":
                    assertEquals("Portland Trail Blazers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "okc":
                    assertEquals("Oklahoma City Thunder", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "min":
                    assertEquals("Minnesota Timberwolves", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "den":
                    assertEquals("Denver Nuggets", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "orl":
                    assertEquals("Orlando Magic", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "wsh":
                    assertEquals("Washington Wizards", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "mia":
                    assertEquals("Miami Heat", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "cha":
                    assertEquals("Charlotte Hornets", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "atl":
                    assertEquals("Atlanta Hawks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "sas":
                    assertEquals("San Antonio Spurs", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "nop":
                    assertEquals("New Orleans Pelicans", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "mem":
                    assertEquals("Memphis Grizzlies", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "hou":
                    assertEquals("Houston Rockets", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "dal":
                    assertEquals("Dallas Mavericks", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "mil":
                    assertEquals("Milwaukee Bucks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
            }
        }
        assertEquals(30, totalTeams);
    }

    private void assertTeamsInfoIsCorrect(Iterable<Team> allTeams) {
        Iterator<Team> teamIterator = allTeams.iterator();
        int totalTeams = 0;
        while (teamIterator.hasNext()) {
            Team team = teamIterator.next();
            totalTeams++;
            switch(team.getTeamId()) {

                case "bos":
                    assertEquals("Boston Celtics", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "bkn":
                    assertEquals("Brooklyn Nets", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "nyk":
                    assertEquals("New York Knicks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "phi":
                    assertEquals("Philadelphia 76ers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "tor":
                    assertEquals("Toronto Raptors", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Atlantic Division", team.getDivision());
                    break;
                case "gsw":
                    assertEquals("Golden State Warriors", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "lac":
                    assertEquals("Los Angeles Clippers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "lal":
                    assertEquals("Los Angeles Lakers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "phx":
                    assertEquals("Phoenix Suns", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "sac":
                    assertEquals("Sacramento Kings", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Pacific Division", team.getDivision());
                    break;
                case "chi":
                    assertEquals("Chicago Bulls", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "cle":
                    assertEquals("Cleveland Cavaliers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "det":
                    assertEquals("Detroit Pistons", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "ind":
                    assertEquals("Indiana Pacers", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
                case "uth":
                    assertEquals("Utah Jazz", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "por":
                    assertEquals("Portland Trail Blazers", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "okc":
                    assertEquals("Oklahoma City Thunder", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "min":
                    assertEquals("Minnesota Timberwolves", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "den":
                    assertEquals("Denver Nuggets", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Northwest Division", team.getDivision());
                    break;
                case "orl":
                    assertEquals("Orlando Magic", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "wsh":
                    assertEquals("Washington Wizards", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "mia":
                    assertEquals("Miami Heat", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "cha":
                    assertEquals("Charlotte Hornets", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "atl":
                    assertEquals("Atlanta Hawks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Southeast Division", team.getDivision());
                    break;
                case "sas":
                    assertEquals("San Antonio Spurs", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "nop":
                    assertEquals("New Orleans Pelicans", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "mem":
                    assertEquals("Memphis Grizzlies", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "hou":
                    assertEquals("Houston Rockets", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "dal":
                    assertEquals("Dallas Mavericks", team.getName());
                    assertEquals("Western Conference", team.getConference());
                    assertEquals("Southwest Division", team.getDivision());
                    break;
                case "mil":
                    assertEquals("Milwaukee Bucks", team.getName());
                    assertEquals("Eastern Conference", team.getConference());
                    assertEquals("Central Division", team.getDivision());
                    break;
            }
        }
        assertEquals(30, totalTeams);
    }
}
