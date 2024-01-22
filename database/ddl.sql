-- 球员表
CREATE TABLE players (
    player_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    birth_date DATE,
    height INT,
    weight INT,
    position VARCHAR(100), -- 后卫、前锋等
    identity VARCHAR(100), -- 本科生，研究生，教职工，其他
    shu_yuan VARCHAR(100), -- 若本科生则有书院
    college VARCHAR(255), -- 若本科生/研究生/教职工则有院系
    admission_year INT, -- 入学年份
    bio TEXT -- 个人简介
);

-- 教练表
CREATE TABLE coaches (
    coach_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    bio TEXT
);

-- 球队表
CREATE TABLE teams (
    team_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    logo_url VARCHAR(255),
    uniform_url VARCHAR(255),
    captain_id INT REFERENCES players(player_id)
);

CREATE TABLE team_uniform (
    
);

-- 球队管理者表
CREATE TABLE team_managers (
    manager_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    organization VARCHAR(255),
    contact_info VARCHAR(255)
);

-- 赛事表
CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    organizer VARCHAR(255),
    level VARCHAR(100),
    schedule TEXT
);

-- 赛事管理者表
CREATE TABLE event_managers (
    manager_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    organization VARCHAR(255),
    contact_info VARCHAR(255)
);

-- 比赛表
CREATE TABLE matches (
    match_id SERIAL PRIMARY KEY,
    home_team_id INT REFERENCES teams(team_id),
    away_team_id INT REFERENCES teams(team_id),
    match_time TIMESTAMP,
    home_team_score INT,
    away_team_score INT,
    event_id INT REFERENCES events(event_id)
);

-- 用户表
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 球队和球员/教练的关系
CREATE TABLE team_members (
    team_id INT REFERENCES teams(team_id),
    member_id INT, -- 可以是球员或教练
    member_type VARCHAR(50), -- 'player' 或 'coach'
    PRIMARY KEY (team_id, member_id, member_type)
);

-- 赛事和球队的关系
CREATE TABLE event_teams (
    event_id INT REFERENCES events(event_id),
    team_id INT REFERENCES teams(team_id),
    PRIMARY KEY (event_id, team_id)
);

-- 用户和球员/教练的关系
CREATE TABLE user_members (
    user_id INT REFERENCES users(user_id),
    member_id INT, -- 可以是球员或教练
    member_type VARCHAR(50), -- 'player' 或 'coach'
    PRIMARY KEY (user_id, member_id, member_type)
);

-- 用户和球队的关系
CREATE TABLE user_teams (
    user_id INT REFERENCES users(user_id),
    team_id INT REFERENCES teams(team_id),
    PRIMARY KEY (user_id, team_id)
);

-- 用户和赛事的关系
CREATE TABLE user_events (
    user_id INT REFERENCES users(user_id),
    event_id INT REFERENCES events(event_id),
    PRIMARY KEY (user_id, event_id)
);
