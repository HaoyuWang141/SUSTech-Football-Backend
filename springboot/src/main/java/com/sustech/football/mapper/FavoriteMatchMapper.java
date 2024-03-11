package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.FavoriteMatch;
import com.sustech.football.entity.Match;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavoriteMatchMapper extends MppBaseMapper<FavoriteMatch> {
    @Select("select * from favorite_match where user_id=#{userId}")
    @Results({
            @Result(column = "match_id", property = "matchId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "match_id", property = "match", one = @One(select = "com.sustech.football.mapper.MatchMapper.selectById"))
    })
    List<FavoriteMatch> selectFavoriteWithUser(Long userId);
}
