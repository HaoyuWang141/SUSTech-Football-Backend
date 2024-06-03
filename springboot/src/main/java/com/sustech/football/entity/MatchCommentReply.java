package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_comment_reply")
@Schema(description = "比赛评论回复对象")
public class MatchCommentReply {
    @TableId(type = IdType.AUTO)
    @Schema(description = "回复 ID", example = "1")
    private Long replyId;

    @Schema(description = "回复的用户 ID", example = "1")
    private Long userId;

    @Schema(description = "回复时间", example = "2024-05-02 13:00:00")
    private Timestamp time;

    @Schema(description = "回复评论 ID", example = "1")
    private Long commentId;

    @Schema(description = "回复内容", example = "同意你的观点！")
    private String content;
}
