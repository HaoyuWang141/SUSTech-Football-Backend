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
@Schema(description = "比赛球员对象")
public class MatchPlayer {
    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "球员 ID", example = "1")
    private Long playerId;

    @Schema(description = "球员号码", example = "1")
    private Integer number;

    @Schema(description = "是否为首发", example = "true")
    private Boolean isStart;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    @TableField(exist = false)
    @Schema(description = "球员信息")
    private Player player;
}
