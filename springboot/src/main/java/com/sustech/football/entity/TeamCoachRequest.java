package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Long teamId;
    @MppMultiId
    private Long coachId;
    private String status;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Coach coach;
}
