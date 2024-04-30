package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.MatchComment;
import com.sustech.football.model.comment.VoMatchComment;

import java.util.List;

public interface MatchCommentService extends IService<MatchComment> {

    List<VoMatchComment> getCommentsWithReplyByMatchId(Long matchId);

}
