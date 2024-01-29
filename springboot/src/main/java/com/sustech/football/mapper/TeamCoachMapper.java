
package com.sustech.football.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.TeamCoach;

public interface TeamCoachMapper extends MppBaseMapper<TeamCoach> {
    @Select("select * from team_coach where coach_id=#{coachId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "coach_id", property = "coachId"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<TeamCoach> selectListWithTeam(Long coachId);

    @Select("select * from team_coach where team_id=#{teamId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "coach_id", property = "coachId"),
            @Result(column = "coach_id", property = "coach", one = @One(select = "com.sustech.football.mapper.CoachMapper.selectById")),
    })
    List<TeamCoach> selectListWithCoach(Long teamId);
}