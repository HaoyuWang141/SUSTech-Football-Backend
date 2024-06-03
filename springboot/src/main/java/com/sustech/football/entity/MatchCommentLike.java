package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("match_comment_like")
@Schema(description = "比赛评论点赞对象")
public class MatchCommentLike {
    @MppMultiId
    @Schema(description = "评论 ID", example = "1")
    private Long commentId;

    @MppMultiId
    @Schema(description = "点赞用户 ID", example = "1")
    private Long userId;
}
