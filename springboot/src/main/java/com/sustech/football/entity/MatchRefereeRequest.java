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
@Schema(description = "比赛裁判邀请对象")
public class MatchRefereeRequest {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "裁判 ID", example = "1")
    private Long refereeId;

    @Schema(description = "邀请状态", example = "PENDING")
    private String status;

    @Schema(description = "邀请最后更新时间", example = "2024-06-01 12:00:00")
    private Timestamp lastUpdated;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    @TableField(exist = false)
    @Schema(description = "裁判信息")
    private Referee referee;

    public MatchRefereeRequest(Long matchId, Long refereeId) {
        this.matchId = matchId;
        this.refereeId = refereeId;
        this.status = STATUS_PENDING;
    }

    public MatchRefereeRequest(Long matchId, Long refereeId, String status) {
        this.matchId = matchId;
        this.refereeId = refereeId;
        this.status = status;
    }
}
