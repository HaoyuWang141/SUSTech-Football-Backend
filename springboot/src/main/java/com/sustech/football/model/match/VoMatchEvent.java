package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛所属赛事 DTO")
public class VoMatchEvent {
    @Schema(description = "赛事 ID", example = "1")
    Long eventId;

    @Schema(description = "赛事名称", example = "2024 年书院杯")
    String eventName;

    @Schema(description = "比赛所在赛事阶段", example = "小组赛")
    String stage;

    @Schema(description = "比赛所在赛事标签", example = "A 组")
    String tag;
}
