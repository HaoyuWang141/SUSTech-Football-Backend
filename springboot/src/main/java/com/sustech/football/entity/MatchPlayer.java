package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchPlayer {
    @MppMultiId
    private Long matchId;
    @MppMultiId
    private Long teamId;
    @MppMultiId
    private Long playerId;
    private Integer number;
    private Boolean isStart;
    @TableField(exist = false)
    private Match match;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Player player;
}
