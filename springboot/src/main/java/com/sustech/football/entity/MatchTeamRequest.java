package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchTeamRequest {
    public static final String TYPE_HOME = "HOME";
    public static final String TYPE_AWAY = "AWAY";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @Schema(description = "球队类型", example = "HOME, AWAY")
    private String type;

    @Schema(description = "邀请状态", example = "PENDING, ACCEPTED, REJECTED")
    private String status;

    @Schema(description = "邀请最后更新时间", example = "2024-06-01 12:00:00")
    private Timestamp lastUpdated;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    public MatchTeamRequest(Long matchId, Long teamId, String type) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.type = type;
        this.status = STATUS_PENDING;
    }

    public MatchTeamRequest(Long matchId, Long teamId, String type, String status) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.type = type;
        this.status = status;
    }
}
