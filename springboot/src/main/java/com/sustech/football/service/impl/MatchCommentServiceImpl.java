package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.Query;
import com.sustech.football.entity.MatchComment;
import com.sustech.football.mapper.MatchCommentMapper;
import com.sustech.football.mapper.UserMapper;
import com.sustech.football.model.comment.VoMatchComment;
import com.sustech.football.service.MatchCommentReplyService;
import com.sustech.football.service.MatchCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchCommentServiceImpl extends ServiceImpl<MatchCommentMapper, MatchComment> implements MatchCommentService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MatchCommentReplyService matchCommentReplyService;

    @Override
    public List<VoMatchComment> getCommentsWithReplyByMatchId(Long matchId) {
        List<MatchComment> matchComments = baseMapper.selectList(new QueryWrapper<MatchComment>().eq("match_id", matchId));
        List<VoMatchComment> voMatchComments = matchComments.stream().map(matchComment -> {
            VoMatchComment voMatchComment = new VoMatchComment(matchComment);
            voMatchComment.setReplyList(matchCommentReplyService.getRepliesByCommentId(matchComment.getCommentId()));
            voMatchComment.setUser(userMapper.selectUserBrief(matchComment.getUserId()));
            return voMatchComment;
        }).toList();
        return voMatchComments;
    }
}
