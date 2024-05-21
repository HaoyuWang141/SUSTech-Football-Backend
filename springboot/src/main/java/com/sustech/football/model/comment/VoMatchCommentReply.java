package com.sustech.football.model.comment;

import com.sustech.football.entity.MatchCommentReply;
import com.sustech.football.entity.User;
import com.sustech.football.model.match.VoMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchCommentReply {
    Long replyId;
    Long userId;
    Long commentId;
    String content;
    Timestamp time;

    User user;

    public VoMatchCommentReply(MatchCommentReply matchCommentReply){
        this.replyId = matchCommentReply.getReplyId();
        this.userId = matchCommentReply.getUserId();
        this.commentId = matchCommentReply.getCommentId();
        this.content = matchCommentReply.getContent();
        this.time = matchCommentReply.getTime();
    }
}
