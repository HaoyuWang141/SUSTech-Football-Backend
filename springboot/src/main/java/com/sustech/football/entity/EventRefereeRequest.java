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
@Schema(description = "赛事裁判请求对象")
public class EventRefereeRequest {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "裁判 ID", example = "1")
    private Long refereeId;

    @Schema(description = "状态", example = "PENDING")
    private String status;

    @Schema(description = "状态最后更新时间", example = "2024-05-01 00:00:00")
    private Timestamp lastUpdated;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "裁判信息")
    private Referee referee;

    public EventRefereeRequest(Long eventId, Long refereeId) {
        this.eventId = eventId;
        this.refereeId = refereeId;
        this.status = STATUS_PENDING;
    }

    public EventRefereeRequest(Long eventId, Long refereeId, String status) {
        this.eventId = eventId;
        this.refereeId = refereeId;
        this.status = status;
    }
}
