package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRefereeRequest {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long refereeId;
    private String status;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
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
