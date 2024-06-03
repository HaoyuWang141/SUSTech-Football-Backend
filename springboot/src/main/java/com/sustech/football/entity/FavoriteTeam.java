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
@Schema(description = "用户收藏球队对象")
public class FavoriteTeam {
    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    public FavoriteTeam(Long userId, Long favoriteId) {
        this.userId = userId;
        this.teamId = favoriteId;
    }
}
