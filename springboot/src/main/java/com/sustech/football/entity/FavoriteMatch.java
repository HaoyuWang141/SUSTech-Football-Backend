package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户收藏比赛对象")
public class FavoriteMatch {
    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    public FavoriteMatch(Long userId, Long matchId) {
        this.userId = userId;
        this.matchId = matchId;
    }
}
