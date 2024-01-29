
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventTeam;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EventTeamMapper extends MppBaseMapper<EventTeam> {
    @Select("select * from event_team where team_id = #{teamId}")
    @Results({
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "event", column = "event_id", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById")),
    })
    List<EventTeam> selectListWithEvent(Long teamId);

    @Select("select * from event_team where event_id = #{eventId}")
    @Results({
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "team", column = "team_id", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<EventTeam> selectListWithTeam(Long eventId);
}