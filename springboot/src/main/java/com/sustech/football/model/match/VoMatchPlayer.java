package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛球员信息 DTO")
public class VoMatchPlayer {

    @Schema(description = "球员 ID", example = "1")
    Long playerId;

    @Schema(description = "球员号码", example = "10")
    Integer number;

    @Schema(description = "球员姓名", example = "李四")
    String name;

    @Schema(description = "球员头像链接", example = "https://example.com:8085/download?filename=li_si.jpg")
    String photoUrl;

    @Schema(description = "是否首发", example = "true")
    Boolean isStart;
}
