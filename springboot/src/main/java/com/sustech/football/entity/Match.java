package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛对象")
public class Match {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ONGOING = "ONGOING";
    public static final String STATUS_FINISHED = "FINISHED";

    @TableId(type = IdType.AUTO)
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @Schema(description = "主队 ID", example = "1")
    private Long homeTeamId;

    @Schema(description = "客队 ID", example = "2")
    private Long awayTeamId;

    @Schema(description = "比赛时间", example = "2024-05-01 00:00:00")
    private Timestamp time;

    @Schema(description = "比赛状态", example = "PENDING, ONGOING, FINISHED")
    private String status;

    @Schema(description = "比赛描述", example = "小组赛第一轮")
    private String description;

    @Schema(description = "主队得分", example = "5")
    private Integer homeTeamScore;

    @Schema(description = "客队得分", example = "4")
    private Integer awayTeamScore;

    @Schema(description = "主队点球数", example = "4")
    private Integer homeTeamPenalty;

    @Schema(description = "客队点球数", example = "3")
    private Integer awayTeamPenalty;

    @TableField(exist = false)
    @Schema(description = "主队信息对象")
    private Team homeTeam;

    @TableField(exist = false)
    @Schema(description = "客队信息对象")
    private Team awayTeam;

    @TableField(exist = false)
    @Schema(description = "裁判列表")
    private List<Referee> refereeList;

    @TableField(exist = false)
    @Schema(description = "比赛球员事件列表")
    private List<MatchPlayerAction> matchPlayerActionList;

    @TableField(exist = false)
    @Schema(description = "比赛所属赛事对象")
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
