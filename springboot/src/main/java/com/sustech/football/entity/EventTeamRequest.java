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
@Schema(description = "赛事队伍参加请求对象")
public class EventTeamRequest {
    public static final String TYPE_APPLICATION = "APPLICATION";
    public static final String TYPE_INVITATION = "INVITATION";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "队伍 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "类型", example = "APPLICATION")
    private String type;

    @Schema(description = "状态", example = "PENDING")
    private String status;

    @Schema(description = "状态最后更新时间", example = "2024-05-01 00:00:00")
    private Timestamp lastUpdated;

    private Boolean hasRead;

    private Boolean isDeleted;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "队伍信息")
    private Team team;
}
