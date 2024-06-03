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
@Schema(description = "球队教练邀请对象")
public class TeamCoachRequest {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    public TeamCoachRequest(Long teamId, Long coachId) {
        this.teamId = teamId;
        this.coachId = coachId;
        this.status = STATUS_PENDING;
    }

    public TeamCoachRequest(Long teamId, Long coachId, String status) {
        this.teamId = teamId;
        this.coachId = coachId;
        this.status = status;
    }

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "教练 ID", example = "1")
    private Long coachId;

    @Schema(description = "邀请状态", example = "PENDING, ACCEPTED, REJECTED")
    private String status;

    @Schema(description = "邀请最后更新时间", example = "2024-06-01 12:00:00")
    private Timestamp lastUpdated;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    @TableField(exist = false)
    @Schema(description = "教练信息")
    private Coach coach;
}
