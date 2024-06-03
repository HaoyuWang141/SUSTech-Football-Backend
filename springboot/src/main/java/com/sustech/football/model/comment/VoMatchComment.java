package com.sustech.football.model.comment;

import com.sustech.football.entity.MatchComment;
import com.sustech.football.entity.User;
import com.sustech.football.model.match.VoMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛评论 DTO")
public class VoMatchComment {
    @Schema(description = "评论 ID", example = "1")
    Long commentId;

    @Schema(description = "比赛 ID", example = "1")
    Long matchId;

    @Schema(description = "评论用户 ID", example = "1")
    Long userId;

    @Schema(description = "评论内容", example = "这场比赛太精彩了！")
    String content;

    @Schema(description = "评论时间", example = "2024-06-01 15:00:00")
    Timestamp time;

    @Schema(description = "评论用户信息")
    User user;

    @Schema(description = "评论回复 DTO 列表")
    List<VoMatchCommentReply> replyList;

    public VoMatchComment(MatchComment matchComment){
        this.commentId = matchComment.getCommentId();
        this.matchId = matchComment.getMatchId();
        this.userId = matchComment.getUserId();
        this.content = matchComment.getContent();
        this.time = matchComment.getTime();
    }
}
