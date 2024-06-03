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
@Schema(description = "球队球员对象")
public class TeamPlayer {
    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "球员 ID", example = "1")
    private Long playerId;

    @Schema(description = "球员球队号码", example = "10")
    private Integer number;

    @TableField(exist = false)
    @Schema(description = "登场次数", example = "10")
    private Integer appearances;

    @TableField(exist = false)
    @Schema(description = "进球数", example = "5")
    private Integer goals;

    @TableField(exist = false)
    @Schema(description = "助攻数", example = "3")
    private Integer assists;

    @TableField(exist = false)
    @Schema(description = "黄牌数", example = "2")
    private Integer yellowCards;

    @TableField(exist = false)
    @Schema(description = "红牌数", example = "1")
    private Integer redCards;

    @TableField(exist = false)
    @Schema(description = "所属球队信息")
    private Team team;

    @TableField(exist = false)
    @Schema(description = "球员信息")
    private Player player;

    public TeamPlayer(Long teamId, Long playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPlayer that = (TeamPlayer) o;
        return teamId.equals(that.teamId) && playerId.equals(that.playerId);
    }

    @Override
    public int hashCode() {
        return teamId.hashCode() + playerId.hashCode();
    }
}