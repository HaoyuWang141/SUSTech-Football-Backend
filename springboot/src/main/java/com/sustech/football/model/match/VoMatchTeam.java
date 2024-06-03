package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛队伍 DTO")
public class VoMatchTeam {
    @Schema(description = "队伍 ID", example = "1")
    Long teamId;

    @Schema(description = "队伍名称", example = "致新书院1队")
    String name;

    @Schema(description = "队伍队徽 URL", example = "https://example.com/logo.jpg")
    String logoUrl;

    @Schema(description = "队伍得分", example = "3")
    Integer score;

    @Schema(description = "队伍点球进球数", example = "2")
    Integer penalty;

    @Schema(description = "队伍球员 DTO 列表")
    List<VoMatchPlayer> players;
}
