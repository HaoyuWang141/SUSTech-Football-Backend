package com.sustech.football.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.TeamCoachRequest;

public interface TeamCoachRequestMapper extends MppBaseMapper<TeamCoachRequest> {
    @Select("select * from team_coach_request where coach_id=#{coachId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "coach_id", property = "coachId"),
            @Result(column = "status", property = "status"),
            @Result(column = "last_updated", property = "lastUpdated"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<TeamCoachRequest> selectListWithTeam(Long coachId);

    @Select("select * from team_coach_request where team_id=#{teamId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "coach_id", property = "coachId"),
            @Result(column = "status", property = "status"),
            @Result(column = "last_updated", property = "lastUpdated"),
            @Result(column = "coach_id", property = "coach", one = @One(select = "com.sustech.football.mapper.CoachMapper.selectById")),
    })
    List<TeamCoachRequest> selectListWithCoach(Long teamId);
}
