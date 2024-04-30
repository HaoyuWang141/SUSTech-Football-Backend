package com.sustech.football.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.football.entity.FavoriteUser;
import com.sustech.football.entity.MatchComment;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchCommentMapper extends BaseMapper<MatchComment> {
}
