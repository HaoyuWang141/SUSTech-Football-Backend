package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventReferee {
    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long refereeId;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
    private Referee referee;

    public EventReferee(Long eventId, Long refereeId) {
        this.eventId = eventId;
        this.refereeId = refereeId;
    }
}
