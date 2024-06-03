package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛球员事件 DTO")
public class VoMatchPlayerAction {
    @Schema(description = "球员所属队伍 ID", example = "1")
    Long teamId;

    @Schema(description = "球员信息 DTO")
    VoMatchPlayer player;

    @Schema(description = "事件发生时间", example = "60")
    Integer time;

    @Schema(description = "事件类型", example = "GOAL, ASSIST, YELLOW_CARD, RED_CARD, SUBSTITUTION")
    String action;
}
