package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @TableId(type = IdType.AUTO)
    private Long matchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Timestamp time;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer homeTeamPenalty;
    private Integer awayTeamPenalty;
    @TableField(exist = false)
    private Team homeTeam;
    @TableField(exist = false)
    private Team awayTeam;
    @TableField(exist = false)
    private List<Referee> refereeList;
    @TableField(exist = false)
    private List<MatchPlayerAction> matchPlayerActionList;
    @TableField(exist = false)
    private MatchEvent matchEvent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return matchId.equals(match.matchId);
    }

    @Override
    public int hashCode() {
        return matchId.hashCode();
    }
}
