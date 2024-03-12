
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.TeamManager;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface TeamManagerMapper extends MppBaseMapper<TeamManager> {
    @Select("select * from team_manager where user_id=#{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "is_owner", property = "isOwner"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<TeamManager> selectTeamWithManager(Long userId);
}