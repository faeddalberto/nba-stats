CREATE KEYSPACE IF NOT EXISTS nba WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor': 2};

CREATE TYPE IF NOT EXISTS nba.made_attempted_stat (
    made int,
    attempted int
);

CREATE TABLE IF NOT EXISTS nba.player (
    player_id uuid,
    name text,
    dob timestamp,
    country text,
    drafted text,
    PRIMARY KEY (player_id)
);

CREATE TABLE IF NOT EXISTS nba.player_career_by_name (
    name text,
    year int,
    team text,
    player_id uuid,
    country text static,
    dob timestamp static,
    drafted text static,
    role text static,
    PRIMARY KEY (name, year, team, player_id)
) WITH CLUSTERING ORDER BY (year DESC, team ASC, player_id ASC);

CREATE TABLE IF NOT EXISTS nba.team (
    team_id text,
    name text,
    conference text,
    division text,
    PRIMARY KEY (team_id)
);

CREATE TABLE IF NOT EXISTS nba.teams_by_conference_division (
    conference text,
    division text,
    team_id text,
    name text,
    PRIMARY KEY (conference, division, team_id)
);

CREATE TABLE IF NOT EXISTS nba.team_roster_by_year (
    year int,
    team text,
    player_id uuid,
    player_name text,
    role text,
    PRIMARY KEY ((year, team), player_id)
);

CREATE TABLE IF NOT EXISTS nba.game (
    game_id uuid,
    season int,
    date timestamp,
    season_type text,
    home_team text,
    home_team_score int,
    visitor_team text,
    visitor_team_score int,
    PRIMARY KEY (game_id)
);

CREATE TABLE IF NOT EXISTS nba.game_by_season_teams (
    season int,
    home_team text,
    visitor_team text,
    date timestamp,
    game_id uuid,
    home_team_score int,
    visitor_team_score int,
    season_type text,
    PRIMARY KEY((season, home_team, visitor_team), date, game_id)
) WITH CLUSTERING ORDER BY (date ASC, game_id ASC);

CREATE TABLE IF NOT EXISTS nba.player_stats_by_game (
    game_id uuid,
    player_id uuid,
    date timestamp,
    season int,
    player_team text,
    opponent_team text,
    mins_played int,
    player_name text,
    fg_ma frozen<made_attempted_stat>,
    tp_ma frozen<made_attempted_stat>,
    ft_ma frozen<made_attempted_stat>,
    off_reb int,
    def_reb int,
    tot_reb int,
    ast int,
    stl int,
    bks int,
    tov int,
    pf int,
    pm int,
    pts int,
    PRIMARY KEY (game_id, player_id)
);

CREATE TABLE IF NOT EXISTS nba.player_stats_by_opponent (
    opponent_team text,
    player_name text,
    player_team text,
    season int,
    date timestamp,
    game_id uuid,
    player_id uuid,
    mins_played int,
    fg_ma frozen<made_attempted_stat>,
    tp_ma frozen<made_attempted_stat>,
    ft_ma frozen<made_attempted_stat>,
    off_reb int,
    def_reb int,
    tot_reb int,
    ast int,
    stl int,
    bks int,
    tov int,
    pf int,
    pm int,
    pts int,
    PRIMARY KEY ((opponent_team, player_name), player_team, season, date, game_id, player_id)
) WITH CLUSTERING ORDER BY (player_team ASC, season DESC, date DESC, game_id ASC, player_id ASC);

CREATE TABLE IF NOT EXISTS nba.stats_by_season (
    season int,
    month int,
    player_team text,
    player_name text,
    opponent_team text,
    game_id uuid,
    player_id uuid,
    date timestamp,
    mins_played int,
    fg_ma frozen<made_attempted_stat>,
    tp_ma frozen<made_attempted_stat>,
    ft_ma frozen<made_attempted_stat>,
    off_reb int,
    def_reb int,
    tot_reb int,
    ast int,
    stl int,
    bks int,
    tov int,
    pf int,
    pm int,
    pts int,
    PRIMARY KEY (season, month, player_team, player_name, opponent_team, game_id, player_id)
);