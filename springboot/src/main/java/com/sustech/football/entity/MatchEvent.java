package com.sustech.football.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEvent {
    private Long eventId;
    private String matchStage;
    private String matchTag;
    private String eventName;

    public MatchEvent(EventMatch eventMatch) {
        if (eventMatch == null) {
            this.eventName = "自由比赛";
            return;
        }
        this.eventId = eventMatch.getEventId();
        this.matchStage = eventMatch.getStage();
        this.matchTag = eventMatch.getTag();
        this.eventName = eventMatch.getEvent().getName();
    }
}
