package com.sustech.football.model.comment;

import com.sustech.football.entity.MatchCommentReply;
import com.sustech.football.entity.User;
import com.sustech.football.model.match.VoMatch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛评论回复 DTO")
public class VoMatchCommentReply {
    @Schema(description = "回复 ID", example = "1")
    Long replyId;

    @Schema(description = "回复用户 ID", example = "1")
    Long userId;

    @Schema(description = "回复的评论 ID", example = "1")
    Long commentId;

    @Schema(description = "回复内容", example = "同意你的观点！")
    String content;

    @Schema(description = "回复时间", example = "2024-06-01 15:00:00")
    Timestamp time;

    @Schema(description = "回复用户信息")
    User user;

    public VoMatchCommentReply(MatchCommentReply matchCommentReply){
        this.replyId = matchCommentReply.getReplyId();
        this.userId = matchCommentReply.getUserId();
        this.commentId = matchCommentReply.getCommentId();
        this.content = matchCommentReply.getContent();
        this.time = matchCommentReply.getTime();
    }
}
