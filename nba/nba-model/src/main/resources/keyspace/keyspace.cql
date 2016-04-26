CREATE KEYSPACE IF NOT EXISTS nba WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor': 2};

USE KEYSPACE nba;

CREATE TYPE IF NOT EXISTS position (
    short_name text,
    long_name text
);

CREATE TYPE IF NOT EXISTS stat (
    made int,
    attempted int
)

CREATE TABLE IF NOT EXISTS player (
    player_id varchar,
    name varchar,
    dob timestamp,
    country varchar,
    drafted varchar,
    PRIMARY KEY (player_id)
);

CREATE TABLE IF NOT EXISTS player_by_country (
    country varchar,
    player_id varchar,
    name varchar,
    dob timestamp,
    country varchar,
    drafted varchar,
    PRIMARY KEY (country, player_id)
);

CREATE TABLE IF NOT EXISTS team (
    team_id text,
    name text,
    conference text,
    division text,
    PRIMARY KEY (team_id)
);

CREATE TABLE IF NOT EXISTS teams_by_conference_division (
    conference text,
    division text,
    team_id text,
    name text,
    PRIMARY KEY (conference, division, team_id)
);

CREATE TABLE IF NOT EXISTS player_career (
    player_name varchar,
    year int,
    team text,
    player_id text,
    role frozen<position>,
    PRIMARY KEY (player_name, year, team, player_id)
) WITH CLUSTERING ORDER BY (year DESC, team ASC);


CREATE TABLE IF NOT EXISTS team_roster_per_year (
    team text,
    year int,
    player_id text,
    player_name text,
    role frozer<position>,
    PRIMARY KEY (team, year, player_id)
) WITH CLUSTERING ORDER BY (year DESC, player_id ASC);


CREATE TABLE IF NOT EXISTS game (
    game_id text,
    season int,
    season_type text,
    date timestamp,
    home_team text,
    home_team_score int,
    visitor_team text,
    visitor_team_score int,
    PRIMARY KEY (id, season, season_type, date)
) WITH CLUSTERING KEY ORDER BY (season DESC, season_type DESC, date ASC);

CREATE TABLE IF NOT EXISTS games_by_season_teams (
    season int,
    home_team text,
    visitor_team text,
    date timestamp,
    game_id text,
    home_team_score int,
    visitor_team_score int,
    season_type text,
    PRIMARY KEY((season, home_team, visitor_team), date, game_id)
) WITH CLUSTERING ORDER BY (date DESC, game_id ASC);

CREATE TABLE IF NOT EXISTS player_stats_by_game (
    game_id text,
    player_id text,
    season int,
    date timestamp,
    player_team text,
    opponent_team text,
    mins_played int,
    player_name text,
    fg frozen<stat>,
    tp frozen<stat>,
    ft frozen<stat>,
    off_reb int,
    def_reb int,
    tot_reb int,
    ast int,
    st int,
    bs int,
    to int,
    pf int,
    pm int,
    pts int
    PRIMARY KEY (game_id, date, player_id)
) WITH CLUSTERING ORDER BY (date DESC, player_id ASC);

CREATE TABLE IF NOT EXISTS player_stats_by_opponent (
    opponent_team text,
    player_name text,
    player_team text,
    season int,
    date timestamp,
    played_id text,
    game_id text,
    mins_played int,
    fg frozen<stat>,
    tp frozen<stat>,
    ft frozen<stat>,
    off_reb int,
    def_reb int,
    tot_reb int,
    ast int,
    st int,
    bs int,
    to int,
    pf int,
    pm int,
    pts int
    PRIMARY KEY ((opponent_team, player_name, player_team), season, date, player_id, game_id)
) WITH CLUSTERING ORDER BY (season DESC, date DESC, player_id ASC, game_id ASC);