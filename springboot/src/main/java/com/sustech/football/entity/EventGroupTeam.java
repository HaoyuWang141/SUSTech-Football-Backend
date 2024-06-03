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
@Schema(description = "赛事分组球队对象")
public class EventGroupTeam {
    @MppMultiId
    @Schema(description = "赛事分组 ID", example = "1")
    private Long groupId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @Schema(description = "胜场数", example = "1")
    private Integer numWins;

    @Schema(description = "平局数", example = "1")
    private Integer numDraws;

    @Schema(description = "负场数", example = "1")
    private Integer numLosses;

    @Schema(description = "进球数", example = "1")
    private Integer numGoalsFor;

    @Schema(description = "失球数", example = "1")
    private Integer numGoalsAgainst;

    @Schema(description = "积分", example = "1")
    private Integer score;

    @TableField(exist = false)
    @Schema(description = "赛事分组信息")
    private EventGroup eventGroup;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    public EventGroupTeam(Long groupId, Long teamId) {
        this.groupId = groupId;
        this.teamId = teamId;
        this.numWins = 0;
        this.numDraws = 0;
        this.numLosses = 0;
        this.numGoalsFor = 0;
        this.numGoalsAgainst = 0;
        this.score = 0;
    }

    public EventGroupTeam(Long groupId, Long teamId, Integer numWins, Integer numDraws, Integer numLosses, Integer numGoalsFor, Integer numGoalsAgainst, Integer score) {
        this.groupId = groupId;
        this.teamId = teamId;
        this.numWins = numWins;
        this.numDraws = numDraws;
        this.numLosses = numLosses;
        this.numGoalsFor = numGoalsFor;
        this.numGoalsAgainst = numGoalsAgainst;
        this.score = score;
    }
}
