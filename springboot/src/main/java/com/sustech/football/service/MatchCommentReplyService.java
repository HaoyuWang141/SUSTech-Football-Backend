package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.MatchCommentReply;
import com.sustech.football.model.comment.VoMatchCommentReply;

import java.util.List;

public interface MatchCommentReplyService extends IService<MatchCommentReply> {

    List<VoMatchCommentReply> getRepliesByCommentId(Long commentId);

}
