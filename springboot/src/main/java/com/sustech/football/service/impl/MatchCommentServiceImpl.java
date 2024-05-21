package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.MatchComment;
import com.sustech.football.entity.MatchCommentLike;
import com.sustech.football.mapper.MatchCommentLikeMapper;
import com.sustech.football.mapper.MatchCommentMapper;
import com.sustech.football.mapper.UserMapper;
import com.sustech.football.model.comment.VoMatchComment;
import com.sustech.football.model.match.VoMatchCommentLike;
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
    MatchCommentLikeMapper matchCommentLikeMapper;

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

    @Override
    public List<VoMatchCommentLike> getCommentLikesByCommentIdList(Long userId, List<Long> commentIds) {

        return commentIds.stream().map(id -> {
            VoMatchCommentLike voMatchCommentLike = new VoMatchCommentLike();
            QueryWrapper<MatchCommentLike> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("comment_id", id);
            List<MatchCommentLike> matchCommentLikes = matchCommentLikeMapper.selectList(queryWrapper);
            List<Long> likeUserIds = matchCommentLikes.stream().map(MatchCommentLike::getUserId).toList();
            voMatchCommentLike.setCommentId(id);
            voMatchCommentLike.setUserId(userId);
            voMatchCommentLike.setLikeNum((long) likeUserIds.size());
            voMatchCommentLike.setHasLiked(likeUserIds.contains(userId));
            return voMatchCommentLike;
        }).toList();
    }

    @Override
    public boolean likeComment(Long commentId, Long userId) {
        return matchCommentLikeMapper.insert(new MatchCommentLike(commentId, userId)) > 0;
    }

    @Override
    public boolean cancelLikeComment(Long commentId, Long userId) {
        return matchCommentLikeMapper.delete(new QueryWrapper<MatchCommentLike>().eq("comment_id", commentId).eq("user_id", userId)) > 0;
    }

    @Override
    public boolean hasLiked(Long commentId, Long userId) {
        return matchCommentLikeMapper.selectCount(new QueryWrapper<MatchCommentLike>().eq("comment_id", commentId).eq("user_id", userId)) > 0;
    }


}
