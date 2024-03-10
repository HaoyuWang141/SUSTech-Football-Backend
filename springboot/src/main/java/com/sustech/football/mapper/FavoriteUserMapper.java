package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.FavoriteTeam;
import com.sustech.football.entity.FavoriteUser;
import com.sustech.football.entity.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavoriteUserMapper extends MppBaseMapper<FavoriteUser> {
    @Select("select * from favorite_user where user_id=#{userId}")
    @Results({
            @Result(column = "favorite_id", property = "userId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "favorite_id", property = "favoriteUser", one = @One(select = "com.sustech.football.mapper.UserMapper.selectById"))
    })
    List<FavoriteUser> selectFavoriteWithUser(Long userId);
}
