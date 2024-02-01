package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamManager {
    @MppMultiId
    private Long userId;
    @MppMultiId
    private Long teamId;
    private Boolean isOwner;
    @TableField(exist = false)
    private Team team;

    public TeamManager(Long userId, Long teamId, Boolean isOwner) {
        this.userId = userId;
        this.teamId = teamId;
        this.isOwner = isOwner;
    }
}
