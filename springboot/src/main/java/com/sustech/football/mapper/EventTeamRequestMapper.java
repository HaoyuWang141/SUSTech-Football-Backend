package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventTeamRequest;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventTeamRequestMapper extends MppBaseMapper<EventTeamRequest> {
    @Select("select * from event_team_request where team_id=#{teamId} and type=#{type}")
    @Results({
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "status", column = "status"),
            @Result(property = "event", column = "event_id", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById")),
    })
    List<EventTeamRequest> selectListWithEvent(Long teamId, String type);

    @Select("select * from event_team_request where event_id=#{eventId} and type=#{type}")
    @Results({
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "status", column = "status"),
            @Result(property = "team", column = "team_id", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<EventTeamRequest> selectListWithTeam(Long eventId, String type);
}
