package com.sustech.football.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.TeamPlayerRequest;

public interface TeamPlayerRequestMapper extends MppBaseMapper<TeamPlayerRequest> {
    @Select("select * from team_player_request where player_id=#{playerId} and type=#{type}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "player_id", property = "playerId"),
            @Result(column = "type", property = "type"),
            @Result(column = "status", property = "status"),
            @Result(column = "team_id", property = "team", one = @One(select = "com.sustech.football.mapper.TeamMapper.selectById")),
    })
    List<TeamPlayerRequest> selectListWithTeam(Long playerId, String type);

    @Select("select * from team_player_request where team_id=#{teamId} and type=#{type}")
    @Results({
            @Result(column = "team_id", property = "teamId"),
            @Result(column = "player_id", property = "playerId"),
            @Result(column = "type", property = "type"),
            @Result(column = "status", property = "status"),
            @Result(column = "player_id", property = "player", one = @One(select = "com.sustech.football.mapper.PlayerMapper.selectById")),
    })
    List<TeamPlayerRequest> selectListWithPlayer(Long teamId, String type);

}
