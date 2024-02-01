package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.MatchRefereeRequest;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchRefereeRequestMapper extends MppBaseMapper<MatchRefereeRequest> {
    @Select("select * from match_referee_request where referee_id=#{refereeId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.matchMapper.selectById")),
    })
    List<MatchRefereeRequest> selectWithMatch(Long refereeId);

    @Select("select * from match_referee_request where match_id=#{matchId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "referee_id", property = "referee", one = @One(select = "com.sustech.football.mapper.refereeMapper.selectById")),
    })
    List<MatchRefereeRequest> selectWithReferee(Long matchId);
}
