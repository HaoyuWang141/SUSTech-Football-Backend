package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoriteMatch {
    @MppMultiId
    private Long userId;

    @MppMultiId
    private Long matchId;

    @TableField(exist = false)
    private Match match;

    public FavoriteMatch(Long userId, Long matchId) {
        this.userId = userId;
        this.matchId = matchId;
    }

    public FavoriteMatch(Long userId, Long matchId, Match match) {
        this.userId = userId;
        this.matchId = matchId;
        this.match = match;
    }
}
