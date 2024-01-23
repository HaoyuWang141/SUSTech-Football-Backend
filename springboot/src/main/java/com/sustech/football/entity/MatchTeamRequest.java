package com.sustech.football.entity;

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
}
