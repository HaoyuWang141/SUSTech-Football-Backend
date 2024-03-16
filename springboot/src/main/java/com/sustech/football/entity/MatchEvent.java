package com.sustech.football.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchEvent {
    private String matchStage;
    private String matchTag;

    public MatchEvent(EventMatch eventMatch) {
        this.matchStage = eventMatch.getStage();
        this.matchTag = eventMatch.getTag();
    }
}
