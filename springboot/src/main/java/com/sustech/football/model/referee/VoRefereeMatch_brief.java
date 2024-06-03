package com.sustech.football.model.referee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "裁判比赛简略信息DTO")
public class VoRefereeMatch_brief {
    @Schema(description = "比赛 ID", example = "1")
    Long matchId;

    @Schema(description = "比赛时间", example = "2024-06-01 15:00:00")
    Timestamp time;

    @Schema(description = "比赛状态", example = "ONGOING, FINISHED, PENDING")
    String status;

    @Schema(description = "主队DTO")
    VoRefereeTeam homeTeam;

    @Schema(description = "客队DTO")
    VoRefereeTeam awayTeam;

    @Schema(description = "赛事DTO")
    VoRefereeEvent event;
}
