package com.sustech.football.entity;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛球员事件对象")
public class MatchPlayerAction {
    public static final String GOAL = "GOAL";
    public static final String ASSIST = "ASSIST";
    public static final String YELLOW_CARD = "YELLOW_CARD";
    public static final String RED_CARD = "RED_CARD";
    public static final String ON = "ON";
    public static final String OFF = "OFF";

    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "球员 ID", example = "1")
    private Long playerId;

    @MppMultiId
    @Schema(description = "事件", example = "GOAL")
    private String action;

    @MppMultiId
    @Schema(description = "比赛时间", example = "1 (min)")
    private Integer time;
}