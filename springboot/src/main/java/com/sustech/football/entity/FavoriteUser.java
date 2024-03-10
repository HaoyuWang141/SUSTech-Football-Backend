package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoriteUser {
    @MppMultiId
    private Long userId;
    @MppMultiId
    private Long favoriteId;
    @TableField(exist = false)
    private User favoriteUser;

    public FavoriteUser(Long userId, Long favoriteId) {
        this.userId = userId;
        this.favoriteId = favoriteId;
    }

    public FavoriteUser(Long userId, Long favoriteId, User favoriteUser) {
        this.userId = userId;
        this.favoriteId = favoriteId;
        this.favoriteUser = favoriteUser;
    }
}
