package com.sustech.football.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGroupTeam {
    @MppMultiId
    private Long groupId;
    @MppMultiId
    private Long teamId;
    private Integer numWins;
    private Integer numDraws;
    private Integer numLosses;
    private Integer numGoalsFor;
    private Integer numGoalsAgainst;
    private Integer score;
    @TableField(exist = false)
    private EventGroup eventGroup;
    @TableField(exist = false)
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
