package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPlayer {
    @MppMultiId
    private Long teamId;
    @MppMultiId
    private Long playerId;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Player player;

    public TeamPlayer(Long teamId, Long playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }
}