-- 用户表
CREATE TABLE t_user
(
    user_id     SERIAL PRIMARY KEY,
    openid      VARCHAR(255) NOT NULL UNIQUE,
    session_key VARCHAR(255) NOT NULL,
    nick_name   VARCHAR(255),
    avatar_url  VARCHAR(255)
);

-- 球员表
CREATE TABLE player
(
    player_id      SERIAL PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    photo_url      VARCHAR(255),
    birth_date     DATE,
    height         INT,
    weight         INT,
    position       VARCHAR(100), -- 后卫、前锋等
    identity       VARCHAR(100), -- 本科生，研究生，教职工，其他
    shu_yuan       VARCHAR(100), -- 若本科生则有书院
    college        VARCHAR(255), -- 若本科生/研究生/教职工则有院系
    admission_year INT,          -- 入学年份
    bio            TEXT,         -- 个人简介
    user_id        INT REFERENCES t_user
);

-- 教练表
CREATE TABLE coach
(
    coach_id  SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    photo_url VARCHAR(255),
    bio       TEXT,
    user_id   INT REFERENCES t_user
);

-- 裁判表
CREATE TABLE referee
(
    referee_id SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    photo_url  VARCHAR(255),
    bio        TEXT,
    user_id    INT REFERENCES t_user
);

-- 球队表
CREATE TABLE team
(
    team_id    SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    logo_url   VARCHAR(255),
    captain_id INT REFERENCES player (player_id)
);

-- 球队-队服
CREATE TABLE team_uniform
(
    team_id     INT REFERENCES team (team_id),
    uniform_url VARCHAR(255),
    PRIMARY KEY (team_id, uniform_url)
);

-- 球队管理者表
CREATE TABLE team_manager
(
    user_id  INT REFERENCES t_user,
    team_id  INT REFERENCES team,
    is_owner BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, team_id)
);

-- 球队-球员
CREATE TABLE team_player
(
    team_id   INT REFERENCES team (team_id),
    player_id INT REFERENCES player (player_id),
    PRIMARY KEY (team_id, player_id)
);

-- 球队邀请球员/球员申请加入球队
CREATE TABLE team_player_request
(
    team_id      INT REFERENCES team (team_id),
    player_id    INT REFERENCES player (player_id),
    type         VARCHAR CHECK ( type IN ('INVITATION', 'APPLICATION') ),
    status       VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_updated TIMESTAMP,
    PRIMARY KEY (team_id, player_id, type)
);

-- 球队-教练
CREATE TABLE team_coach
(
    team_id  INT REFERENCES team (team_id),
    coach_id INT REFERENCES coach (coach_id),
    PRIMARY KEY (team_id, coach_id)
);

-- 球队邀请教练
CREATE TABLE team_coach_request
(
    team_id      INT REFERENCES team (team_id),
    coach_id     INT REFERENCES coach (coach_id),
    status       VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_updated TIMESTAMP,
    PRIMARY KEY (team_id, coach_id)
);

-- 比赛表
CREATE TABLE match
(
    match_id          SERIAL PRIMARY KEY,
    home_team_id      INT REFERENCES team (team_id),
    away_team_id      INT REFERENCES team (team_id),
    time              TIMESTAMP,
    home_team_score   INT DEFAULT 0,
    away_team_score   INT DEFAULT 0,
    home_team_penalty INT DEFAULT 0,
    away_team_penalty INT DEFAULT 0
);

CREATE TABLE match_manager
(
    match_id INT REFERENCES match,
    user_id  INT REFERENCES t_user,
    is_owner BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (match_id, user_id)
);

-- 比赛(友谊赛)邀请球队
CREATE TABLE match_team_request
(
    match_id     INT REFERENCES match,
    team_id      INT REFERENCES team,
    type         VARCHAR CHECK ( type IN ('HOME', 'AWAY') ),
    status       VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_updated TIMESTAMP,
    PRIMARY KEY (match_id, team_id)
);

-- 比赛-裁判
CREATE TABLE match_referee
(
    match_id   INT REFERENCES match,
    referee_id INT REFERENCES referee,
    PRIMARY KEY (match_id, referee_id)
);

-- 比赛邀请裁判
CREATE TABLE match_referee_request
(
    match_id     INT REFERENCES match,
    referee_id   INT REFERENCES referee,
    status       VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_updated TIMESTAMP,
    PRIMARY KEY (match_id, referee_id)
);

-- 比赛-球员行为（进球、红牌、黄牌）
CREATE TABLE match_player_action
(
    match_id  INT REFERENCES match,
    team_id   INT REFERENCES team,
    player_id INT REFERENCES player,
    action    VARCHAR, -- 进球、红牌、黄牌
    time      INTEGER, -- 比赛开始的时间
    PRIMARY KEY (match_id, team_id, player_id, action, time)
);

CREATE TABLE match_live
(
    live_id   SERIAL PRIMARY KEY,
    match_id  INT REFERENCES match,
    live_name VARCHAR(255),
    live_url  VARCHAR(255)
);

CREATE TABLE match_video
(
    video_id   SERIAL PRIMARY KEY,
    match_id   INT REFERENCES match,
    video_name VARCHAR(255),
    video_url  VARCHAR(255)
);

-- 赛事表
CREATE TABLE event
(
    event_id    SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

-- 赛事管理者表
CREATE TABLE event_manager
(
    event_id INT REFERENCES event (event_id),
    user_id  INT REFERENCES t_user,
    is_owner BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (event_id, user_id)
);

-- 赛事-球队
CREATE TABLE event_team
(
    event_id INT REFERENCES event (event_id),
    team_id  INT REFERENCES team (team_id),
    PRIMARY KEY (event_id, team_id)
);

-- 小组
CREATE TABLE event_group
(
    group_id SERIAL PRIMARY KEY,
    event_id INT REFERENCES event (event_id),
    name     VARCHAR(255) NOT NULL
);

-- 小组-球队
CREATE TABLE event_group_team
(
    group_id          INT REFERENCES event_group (group_id),
    team_id           INT REFERENCES team (team_id),
    num_wins          INT DEFAULT 0, -- 胜场
    num_draws         INT DEFAULT 0, -- 平局
    num_losses        INT DEFAULT 0, -- 负场
    num_goals_for     INT DEFAULT 0, -- 进球数
    num_goals_against INT DEFAULT 0, -- 失球数
    score             INT DEFAULT 0, -- 积分
    PRIMARY KEY (group_id, team_id)
);

-- 赛事邀请球队/球队申请加入赛事
CREATE TABLE event_team_request
(
    event_id     INT REFERENCES event (event_id),
    team_id      INT REFERENCES team (team_id),
    type         VARCHAR CHECK ( type IN ('INVITATION', 'APPLICATION') ),
    status       VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_updated TIMESTAMP,
    PRIMARY KEY (event_id, team_id, type)
);

-- 赛事-裁判
CREATE TABLE event_referee
(
    event_id   INT REFERENCES event,
    referee_id INT REFERENCES referee,
    PRIMARY KEY (event_id, referee_id)
);

-- 赛事邀请裁判
CREATE TABLE event_referee_request
(
    event_id    INT REFERENCES event,
    referee_id  INT REFERENCES referee,
    status      VARCHAR CHECK ( status IN ('PENDING', 'ACCEPTED', 'REJECTED') ),
    last_update TIMESTAMP,
    PRIMARY KEY (event_id, referee_id)
);

-- 赛事-比赛阶段：小组赛、淘汰赛、排位赛等
CREATE TABLE event_stage
(
    event_id INT REFERENCES event,
    stage    VARCHAR, -- 小组赛、淘汰赛、排位赛等
    PRIMARY KEY (event_id, stage)
);

-- 【赛事-比赛阶段】-标签：如stage=小组赛，tag=A组、B组等；stage=淘汰赛，tag=1/8决赛、1/4决赛等
CREATE TABLE event_stage_tag
(
    event_id INT REFERENCES event,
    stage    VARCHAR, -- 小组赛、淘汰赛、排位赛等
    tag      VARCHAR, -- A组、B组等；1/8决赛、1/4决赛等
    FOREIGN KEY (event_id, stage) REFERENCES event_stage,
    PRIMARY KEY (event_id, stage, tag)
);

-- 赛事-比赛
CREATE TABLE event_match
(
    event_id INT REFERENCES event,
    match_id INT REFERENCES match,
    stage    VARCHAR,
    tag      VARCHAR,
    PROPERTY VARCHAR,
    PRIMARY KEY (event_id, match_id),
    FOREIGN KEY (event_id, stage) REFERENCES event_stage,
    FOREIGN KEY (event_id, stage, tag) REFERENCES event_stage_tag
);

-- 通知表
CREATE TABLE notification
(
    notification_id SERIAL PRIMARY KEY,
    publisher_id    INT REFERENCES t_user,
    type            VARCHAR CHECK ( type IN ('ALL_USERS',
                                             'TEAM_TO_PLAYER',
                                             'EVENT_TO_TEAM',
                                             'EVENT_TO_PLAYER',
                                             'MATCH_TO_TEAM',
                                             'MATCH_TO_PLAYER') ),
    source_id       INT,
    target_id       INT,
    content         TEXT,
    time            TIMESTAMP
);

-- 收藏用户表
CREATE TABLE favorite_user
(
    user_id     INT REFERENCES t_user (user_id),
    favorite_id INT REFERENCES t_user (user_id),
    PRIMARY KEY (user_id, favorite_id)
);

-- 收藏球队表
CREATE TABLE favorite_team
(
    user_id INT REFERENCES t_user (user_id),
    team_id INT REFERENCES team (team_id),
    PRIMARY KEY (user_id, team_id)
);

-- 收藏赛事表
CREATE TABLE favorite_event
(
    user_id  INT REFERENCES t_user (user_id),
    event_id INT REFERENCES event (event_id),
    PRIMARY KEY (user_id, event_id)
);

-- 收藏比赛表
CREATE TABLE favorite_match
(
    user_id  INT REFERENCES t_user (user_id),
    match_id INT REFERENCES match (match_id),
    PRIMARY KEY (user_id, match_id)
);