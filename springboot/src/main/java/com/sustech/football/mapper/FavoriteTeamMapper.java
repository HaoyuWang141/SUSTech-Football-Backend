package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.FavoriteTeam;
import com.sustech.football.entity.Team;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavoriteTeamMapper extends MppBaseMapper<FavoriteTeam> {
    @Select("select * from favorite_team where user_id=#{userId}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById"))
    })
    List<FavoriteTeam> selectFavoriteWithUser(Long userId);
}
