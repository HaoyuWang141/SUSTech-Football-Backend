package com.sustech.football.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛所属赛事对象")
public class MatchEvent {

    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @Schema(description = "赛事阶段", example = "小组赛")
    private String matchStage;

    @Schema(description = "赛事标签", example = "小组赛")
    private String matchTag;

    @Schema(description = "赛事名称", example = "2024书院杯")
    private String eventName;

    public MatchEvent(EventMatch eventMatch) {
        if (eventMatch == null) {
            this.eventName = "自由比赛";
            return;
        }
        this.eventId = eventMatch.getEventId();
        this.matchStage = eventMatch.getStage();
        this.matchTag = eventMatch.getTag();
        if (eventMatch.getEvent() != null) {
            this.eventName = eventMatch.getEvent().getName();
        }
    }
}
