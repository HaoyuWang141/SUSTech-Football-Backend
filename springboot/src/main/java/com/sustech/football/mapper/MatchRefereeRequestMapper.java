package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.MatchRefereeRequest;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchRefereeRequestMapper extends MppBaseMapper<MatchRefereeRequest> {
    @Select("SELECT * FROM match_referee_request WHERE referee_id=#{refereeId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "status", property = "status"),
            @Result(column = "last_updated", property = "lastUpdated"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById")),
    })
    List<MatchRefereeRequest> selectListWithMatch(Long refereeId);

    @Select("SELECT * FROM match_referee_request WHERE match_id=#{matchId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "status", property = "status"),
            @Result(column = "last_updated", property = "lastUpdated"),
            @Result(column = "referee_id", property = "referee", one = @One(select = "com.sustech.football.mapper.RefereeMapper.selectById")),
    })
    List<MatchRefereeRequest> selectListWithReferee(Long matchId);
}
