package com.sustech.football.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoriteEvent {

    @MppMultiId
    private Long userId;

    @MppMultiId
    private Long eventId;

    @TableField(exist = false)
    private Event event;

    public FavoriteEvent(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public FavoriteEvent(Long userId, Long eventId, Event event) {
        this.userId = userId;
        this.eventId = eventId;
        this.event = event;
    }

}
