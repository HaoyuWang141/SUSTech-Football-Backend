package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.MatchComment;
import com.sustech.football.model.comment.VoMatchComment;
import com.sustech.football.model.match.VoMatchCommentLike;

import java.util.List;

public interface MatchCommentService extends IService<MatchComment> {

    List<VoMatchComment> getCommentsWithReplyByMatchId(Long matchId);

    List<VoMatchCommentLike> getCommentLikesByCommentIdList( Long userId, List<Long> commentId);

    boolean likeComment(Long commentId, Long userId);

    boolean cancelLikeComment(Long commentId, Long userId);

    boolean hasLiked(Long commentId, Long userId);
}
