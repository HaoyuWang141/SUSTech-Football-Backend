
package com.sustech.football.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.sustech.football.entity.UserRole;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserRoleMapper extends MppBaseMapper<UserRole> {
    @Select("SELECT tu.user_id, tu.nick_name, referee_id, coach_id, player_id FROM t_user tu " +
            "LEFT JOIN coach c on tu.user_id = c.user_id " +
            "LEFT JOIN player p on tu.user_id = p.user_id " +
            "LEFT JOIN referee r on tu.user_id = r.user_id " +
            "WHERE (c.coach_id IS NOT NULL OR p.player_id IS NOT NULL OR r.referee_id IS NOT NULL)")
    @Results({
            @Result(column = "user_id", property = "userId"),
            @Result(column = "nick_name", property = "nickName"),
            @Result(column = "player_id", property = "playerRole", one = @One(select = "com.sustech.football.mapper.PlayerMapper.selectById")),
            @Result(column = "coach_id", property = "coachRole", one = @One(select = "com.sustech.football.mapper.CoachMapper.selectById")),
            @Result(column = "referee_id", property = "refereeRole", one = @One(select = "com.sustech.football.mapper.RefereeMapper.selectById"))
    })
    List<UserRole> selectRoleUser();
}