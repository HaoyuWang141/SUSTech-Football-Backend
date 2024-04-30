package com.sustech.football.model.comment;

import com.sustech.football.entity.MatchComment;
import com.sustech.football.entity.User;
import com.sustech.football.model.match.VoMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchComment {
    Long commentId;
    Long matchId;
    Long userId;
    String content;
    Timestamp time;

    User user;
    List<VoMatchCommentReply> replyList;

    public VoMatchComment(MatchComment matchComment){
        this.commentId = matchComment.getCommentId();
        this.matchId = matchComment.getMatchId();
        this.userId = matchComment.getUserId();
        this.content = matchComment.getContent();
        this.time = matchComment.getTime();
    }
}
