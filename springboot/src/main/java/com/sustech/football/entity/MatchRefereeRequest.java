package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRefereeRequest {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    private Long matchId;
    @MppMultiId
    private Long refereeId;
    private String status;
    @TableField(exist = false)
    private Match match;
    @TableField(exist = false)
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
