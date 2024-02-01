
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.MatchReferee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MatchRefereeMapper extends MppBaseMapper<MatchReferee> {
    @Select("select * from match_referee where match_id=#{matchId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "referee_id", property = "referee", one = @One(select = "com.sustech.football.mapper.RefereeMapper.selectById")),
    })
    List<MatchReferee> selectWithReferee(Long matchId);

    @Select("select * from match_referee where referee_id=#{refereeId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById")),
    })
    List<MatchReferee> selectWithMatch(Long refereeId);
}