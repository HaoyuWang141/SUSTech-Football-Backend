package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchManager {
    @MppMultiId
    private Long matchId;
    @MppMultiId
    private Long userId;
    private Boolean isOwner;
    @TableField(exist = false)
    private Match match;

    public MatchManager(Long matchId, Long userId, Boolean isOwner) {
        this.matchId = matchId;
        this.userId = userId;
        this.isOwner = isOwner;
    }
}
