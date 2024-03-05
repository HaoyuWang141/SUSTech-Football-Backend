package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventMatch {
    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long matchId;
    private String stage;
    private String tag;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
    private Match match;

    public EventMatch(Long eventId, Long matchId) {
        this.eventId = eventId;
        this.matchId = matchId;
    }

    public EventMatch(Long eventId, Long matchId, String stage, String tag) {
        this.eventId = eventId;
        this.matchId = matchId;
        this.stage = stage;
        this.tag = tag;
    }
}
