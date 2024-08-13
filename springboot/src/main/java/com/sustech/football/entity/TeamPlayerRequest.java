package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "球队球员申请对象")
public class TeamPlayerRequest {
    public static final String TYPE_APPLICATION = "APPLICATION";
    public static final String TYPE_INVITATION = "INVITATION";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "球员 ID", example = "1")
    private Long playerId;

    @MppMultiId
    @Schema(description = "申请类型", example = "APPLICATION, INVITATION")
    private String type;

    @Schema(description = "申请状态", example = "PENDING, ACCEPTED, REJECTED")
    private String status;

    @Schema(description = "申请最后更新时间", example = "2024-06-01 12:00:00")
    private Timestamp lastUpdated;

    private Boolean hasRead;

    private Boolean isDeleted;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    @TableField(exist = false)
    @Schema(description = "球员信息")
    private Player player;
}
