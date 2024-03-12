
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.EventManager;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EventManagerMapper extends MppBaseMapper<EventManager> {
    @Select("select * from event_manager where user_id=#{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "is_owner", property = "isOwner"),
            @Result(column = "event_id", property = "event", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById")),
    })
    List<EventManager> selectEventWithManager(Long userId);
}