package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTeamRequest {
    public static final String TYPE_APPLICATION = "APPLICATION";
    public static final String TYPE_INVITATION = "INVITATION";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long teamId;
    @MppMultiId
    private String type;
    private String status;
    private Timestamp lastUpdate;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
    private Team team;

    public EventTeamRequest(Long eventId, Long teamId, String type, String status) {
        this.eventId = eventId;
        this.teamId = teamId;
        this.type = type;
        this.status = status;
    }

    public EventTeamRequest(Long eventId, Long teamId, String type) {
        this.eventId = eventId;
        this.teamId = teamId;
        this.type = type;
        this.status = STATUS_PENDING;
    }
}
