
package com.sustech.football.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.One;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.TeamPlayer;

public interface TeamPlayerMapper extends MppBaseMapper<TeamPlayer> {
    @Select("select * from team_player where player_id=#{playerId}")
    @Results({
        @Result(column = "team_id", property = "teamId"),
        @Result(column = "player_id", property = "playerId"),
        @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById"))
    })
    List<TeamPlayer> selectListWithTeam(Long playerId);

    @Select("select * from team_player where team_id=#{teamId}")
    @Results({
        @Result(column = "team_id", property = "teamId"),
        @Result(column = "player_id", property = "playerId"),
        @Result(column = "player_id", property = "player", one = @One(select = "com.sustech.football.mapper.PlayerMapper.selectById"))
    })
    List<TeamPlayer> selectListWithPlayer(Long teamId);
}