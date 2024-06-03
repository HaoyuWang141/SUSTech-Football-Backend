package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "裁判比赛信息 DTO")
public class VoMatchInfoForReferee {
    @Schema(description = "比赛 ID", example = "1")
    Long matchId;

    @Schema(description = "比赛状态", example = "ONGOING, FINISHED, PENDING")
    String status;

    @Schema(description ="主队得分", example = "5")
    Integer homeTeamScore;

    @Schema(description = "客队得分", example = "3")
    Integer awayTeamScore;

    @Schema(description = "主队点球进球数", example = "3")
    Integer homeTeamPenalty;

    @Schema(description = "客队点球进球数", example = "2")
    Integer awayTeamPenalty;
}
