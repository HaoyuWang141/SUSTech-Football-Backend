package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛 DTO")
public class VoMatch {
    @Schema(description = "比赛 ID", example = "1")
    Long matchId;

    @Schema(description = "比赛时间", example = "2024-06-01 15:00:00")
    Timestamp time;

    @Schema(description = "比赛状态", example = "ONGOING, FINISHED, UPCOMING")
    String status;

    @Schema(description = "主队 DTO")
    VoMatchTeam homeTeam;

    @Schema(description = "客队 DTO")
    VoMatchTeam awayTeam;

    @Schema(description = "比赛管理者 ID 列表")
    List<Long> managerList;

    @Schema(description = "裁判 DTO 列表")
    List<VoMatchReferee> refereeList;

    @Schema(description = "比赛球员事件 DTO 列表")
    List<VoMatchPlayerAction> matchPlayerActionList;

    @Schema(description = "比赛赛事 DTO")
    VoMatchEvent matchEvent;
}
