package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛评论点赞 DTO")
public class VoMatchCommentLike {
    @Schema(description = "评论 ID", example = "1")
    Long commentId;

    @Schema(description = "用户 ID", example = "1")
    Long userId;

    @Schema(description = "点赞数", example = "5")
    Long likeNum;

    @Schema(description = "当前是否已点赞", example = "true")
    Boolean hasLiked;
}
