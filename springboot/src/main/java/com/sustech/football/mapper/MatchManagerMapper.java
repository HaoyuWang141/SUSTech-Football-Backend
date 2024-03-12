package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.MatchManager;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchManagerMapper extends MppBaseMapper<MatchManager> {
    @Select("select * from match_manager where user_id=#{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "is_owner", property = "isOwner"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById")),
    })
    List<MatchManager> selectMatchWithManager(Long userId);
}
