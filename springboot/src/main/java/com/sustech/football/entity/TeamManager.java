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
@Schema(description = "球队管理员用户对象")
public class TeamManager {
    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @Schema(description = "是否为创建者", example = "true")
    private Boolean isOwner;

    @TableField(exist = false)
    @Schema(description = "用户信息")
    private Team team;

    public TeamManager(Long userId, Long teamId, Boolean isOwner) {
        this.userId = userId;
        this.teamId = teamId;
        this.isOwner = isOwner;
    }

    public TeamManager(Long userId, Long teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }
}
