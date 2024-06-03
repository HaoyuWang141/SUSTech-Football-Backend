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
@Schema(description = "用户收藏用户对象")
public class FavoriteUser {
    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @MppMultiId
    @Schema(description = "收藏用户 ID", example = "1")
    private Long favoriteId;

    @TableField(exist = false)
    @Schema(description = "收藏用户信息")
    private User favoriteUser;

    public FavoriteUser(Long userId, Long favoriteId) {
        this.userId = userId;
        this.favoriteId = favoriteId;
    }

}
