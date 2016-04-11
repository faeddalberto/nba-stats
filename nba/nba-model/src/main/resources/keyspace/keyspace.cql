CREATE KEYSPACE IF NOT EXISTS nba WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor': 2};

USE KEYSPACE nba;

CREATE TYPE IF NOT EXISTS position (
    ref_num int,
    name varchar
);

CREATE TABLE IF NOT EXISTS player (
    id varchar,
    year int,
    team varchar,
    name varchar static,
    role frozen<position>,
    PRIMARY KEY (id, year, team)
) WITH CLUSTERING ORDER BY (year DESC, team ASC);

CREATE TABLE IF NOT EXISTS team_roster_per_year (
    team varchar,
    year int,
    id varchar,
    name varchar,
    role frozer<position>,
    PRIMARY KEY (team, year, id)
) WITH CLUSTERING ORDER BY (year desc, id ASC);


CREATE TABLE IF NOT EXISTS team (
    id varchar,
    name varchar,
    conference varchar,
    division varchar,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teams_by_conference_division (
    conference varchar,
    division varchar,
    id varchar,
    name varchar,
    PRIMARY KEY (conference, division, id)
);

CREATE TABLE IF NOT EXISTS game (
    id varchar,
    season int,
    season_type varchar,
    home_team varchar,
    visitor_team varchar,
    home_team_score int,
    visitor_team_score int,
    date timestamp,
    PRIMARY KEY (id, season, season_type)
) WITH CLUSTERING KEY ORDER BY (season DESC, season_type DESC);

CREATE TABLE IF NOT EXISTS games_by_season_teams (
    season int,
    home_team varchar,
    visitor_team varchar,
    date timestamp,
    game_id varchar,
    home_team_score int,
    visitor_team_score int,
    season_type varchar,
    PRIMARY KEY((season, home_team, visitor_team), date, game_id)
) WITH CLUSTERING ORDER BY (date DESC, game_id ASC);

