package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventRefereeRequest;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.One;

import java.util.List;

public interface EventRefereeRequestMapper extends MppBaseMapper<EventRefereeRequest> {
    @Select("select * from event_referee_request where referee_id=#{refereeId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "status", property = "status"),
            @Result(column = "event_id", property = "event", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById")),
    })
    List<EventRefereeRequest> selectListWithEvent(Long refereeId);

    @Select("select * from event_referee_request where event_id=#{eventId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "referee_id", property = "refereeId"),
            @Result(column = "status", property = "status"),
            @Result(column = "referee_id", property = "referee", one = @One(select = "com.sustech.football.mapper.RefereeMapper.selectById")),
    })
    List<EventRefereeRequest> selectListWithReferee(Long eventId);
}
