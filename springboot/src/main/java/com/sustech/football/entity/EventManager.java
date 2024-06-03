package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "赛事管理员对象")
public class EventManager {
    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "管理员用户 ID", example = "1")
    private Long userId;

    @Schema(description = "是否为赛事创建者", example = "true")
    private Boolean isOwner;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    public EventManager(Long eventId, Long userId, Boolean isOwner) {
        this.eventId = eventId;
        this.userId = userId;
        this.isOwner = isOwner;
    }
}
