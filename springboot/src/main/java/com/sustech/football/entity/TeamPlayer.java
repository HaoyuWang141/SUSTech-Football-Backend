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
    private Integer number;
    @TableField(exist = false)
    private Integer appearances;
    @TableField(exist = false)
    private Integer goals;
    @TableField(exist = false)
    private Integer assists;
    @TableField(exist = false)
    private Integer yellowCards;
    @TableField(exist = false)
    private Integer redCards;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
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