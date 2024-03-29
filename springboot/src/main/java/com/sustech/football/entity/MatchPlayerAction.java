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
    public static final String GOAL = "GOAL";
    public static final String ASSIST = "ASSIST";
    public static final String YELLOW_CARD = "YELLOW_CARD";
    public static final String RED_CARD = "RED_CARD";
    public static final String ON = "ON";
    public static final String OFF = "OFF";

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