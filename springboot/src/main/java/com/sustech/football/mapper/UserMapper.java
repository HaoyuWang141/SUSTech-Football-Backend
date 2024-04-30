
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper extends MppBaseMapper<User> {
    @Select("SELECT user_id, nick_name, avatar_url FROM t_user WHERE user_id = #{userId}")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "avatar_url", property = "avatarUrl"),
    })
    User selectUserBrief(Long userId);
}