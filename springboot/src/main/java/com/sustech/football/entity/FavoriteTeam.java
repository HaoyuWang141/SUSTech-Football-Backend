package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoriteTeam {
    @MppMultiId
    private Long userId;
    @MppMultiId
    private Long teamId;
    @TableField(exist = false)
    private Team team;

    public FavoriteTeam(Long userId, Long favoriteId) {
        this.userId = userId;
        this.teamId = favoriteId;
    }

    public FavoriteTeam(Long userId, Long teamId, Team team) {
        this.userId = userId;
        this.teamId = teamId;
        this.team = team;
    }
}
