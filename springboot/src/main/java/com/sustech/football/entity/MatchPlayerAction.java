package com.sustech.football.entity;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchPlayerAction {
    @MppMultiId
    private Long matchId;
    @MppMultiId
    private Long teamId;
    @MppMultiId
    private Long playerId;
    @MppMultiId
    private String action;
    @MppMultiId
    private Integer time;
}