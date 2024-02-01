
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventReferee;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EventRefereeMapper extends MppBaseMapper<EventReferee>
{
    @Select("select * from event_referee where referee_id=#{refereeId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "event_id", property = "event", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById")),
    })
    List<EventReferee> selectListWithEvent(Long refereeId);

    @Select("select * from event_referee where event_id=#{eventId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "referee_id", property = "referee", one = @One(select = "com.sustech.football.mapper.RefereeMapper.selectById")),
    })
    List<EventReferee> selectListWithReferee(Long eventId);
}