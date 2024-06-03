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
@NoArgsConstructor
@AllArgsConstructor
@TableName("match_comment")
@Schema(description = "比赛评论对象")
public class MatchComment {
    @TableId(type = IdType.AUTO)
    @Schema(description = "评论 ID", example = "1")
    private Long commentId;

    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @Schema(description = "评论用户 ID", example = "1")
    private Long userId;

    @Schema(description = "评论内容", example = "这场比赛太精彩了！")
    private String content;

    @Schema(description = "评论时间", example = "2024-05-02 12:00:00")
    private Timestamp time;

}
