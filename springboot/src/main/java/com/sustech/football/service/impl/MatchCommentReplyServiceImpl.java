package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.MatchCommentReply;
import com.sustech.football.mapper.MatchCommentReplyMapper;
import com.sustech.football.mapper.UserMapper;
import com.sustech.football.model.comment.VoMatchCommentReply;
import com.sustech.football.service.MatchCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchCommentReplyServiceImpl extends ServiceImpl<MatchCommentReplyMapper, MatchCommentReply> implements MatchCommentReplyService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<VoMatchCommentReply> getRepliesByCommentId(Long commentId) {
        List<MatchCommentReply> matchCommentReplies = baseMapper.selectList(new QueryWrapper<MatchCommentReply>().eq("comment_id", commentId));
        List<VoMatchCommentReply> voMatchCommentReplies = matchCommentReplies.stream().map(VoMatchCommentReply::new).toList();
        for (VoMatchCommentReply voMatchCommentReply : voMatchCommentReplies) {
            voMatchCommentReply.setUser(userMapper.selectUserBrief(voMatchCommentReply.getUserId()));
        }
        return voMatchCommentReplies;
    }
}
