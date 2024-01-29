package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long matchId;
    @MppMultiId
    private Long teamId;
    private String type;
    private String status;
    @TableField(exist = false)
    private Match match;
    @TableField(exist = false)
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
