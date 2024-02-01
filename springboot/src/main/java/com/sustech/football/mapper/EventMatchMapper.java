
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventMatch;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EventMatchMapper extends MppBaseMapper<EventMatch> {
    @Select("select * from event_match where event_id=#{eventId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById"))
    })
    List<EventMatch> selectListWithMatch(Long eventId);
}