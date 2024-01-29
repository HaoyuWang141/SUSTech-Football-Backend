package com.sustech.football.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.One;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.MatchTeamRequest;

public interface MatchTeamRequestMapper extends MppBaseMapper<MatchTeamRequest> {
    @Select("select * from match_team_request where team_id=#{teamId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "type", property = "type"),
            @Result(column = "status", property = "status"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById")),
    })
    List<MatchTeamRequest> listWithMatch(Long teamId);

    @Select("select * from match_team_request where match_id=#{matchId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "type", property = "type"),
            @Result(column = "status", property = "status"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<MatchTeamRequest> listWithTeam(Long matchId);
}
