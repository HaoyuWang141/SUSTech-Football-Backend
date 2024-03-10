package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.FavoriteEvent;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavoriteEventMapper extends MppBaseMapper<FavoriteEvent> {
    @Select("select * from favorite_event where user_id=#{userId}")
    @Results({
            @Result(column = "event_id", property = "eventId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "event_id", property = "event", one = @One(select = "com.sustech.football.mapper.EventMapper.selectById"))
    })
    List<FavoriteEvent> selectFavoriteWithUser(Long userId);
}
