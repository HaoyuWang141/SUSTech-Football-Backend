package com.sustech.football.model.referee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "裁判球队DTO")
public class VoRefereeTeam {
    @Schema(description = "球队 ID", example = "1")
    Long teamId;

    @Schema(description = "球队名称", example = "致新书院1队")
    String name;

    @Schema(description = "球队队徽链接", example = "https://example.com:8085/download?filename=logo.jpg")
    String logoUrl;

    @Schema(description = "球队得分", example = "5")
    Integer score;

    @Schema(description = "球队点球进球数", example = "3")
    Integer penalty;
}
