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
@Schema(description = "用户收藏赛事对象")
public class FavoriteEvent {

    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    public FavoriteEvent(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

}
