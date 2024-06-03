package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "球队对象")
public class Team {
    @TableId(type = IdType.AUTO)
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @Schema(description = "球队名称", example = "致新书院1队")
    private String name;

    @Schema(description = "球队logo链接", example = "www.example.com:8085/download?filename=zhixin_logo.png")
    private String logoUrl;

    @Schema(description = "球队队长 ID", example = "1")
    private Long captainId;

    @Schema(description = "球队简介", example = "我们是致新书院1队")
    private String description;

    @TableField(exist = false)
    @Schema(description = "球队教练列表")
    private List<Coach> coachList;

    @TableField(exist = false)
    @Schema(description = "球队球员列表")
    private List<TeamPlayer> teamPlayerList;

    @TableField(exist = false)
    @Schema(description = "球队队员列表")
    private List<Player> playerList;

    @TableField(exist = false)
    @Schema(description = "球队赛事列表")
    private List<Event> eventList;

    @TableField(exist = false)
    @Schema(description = "球队比赛列表")
    private List<Match> matchList;

    @TableField(exist = false)
    @Schema(description = "球队管理员用户列表")
    private List<User> managerList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId.equals(team.teamId);
    }

    @Override
    public int hashCode() {
        return teamId.hashCode();
    }
}
