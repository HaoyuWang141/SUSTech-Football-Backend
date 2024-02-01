package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventManager {
    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long userId;
    private Boolean isOwner;
    @TableField(exist = false)
    private Event event;

    public EventManager(Long eventId, Long userId, Boolean isOwner) {
        this.eventId = eventId;
        this.userId = userId;
        this.isOwner = isOwner;
    }
}
