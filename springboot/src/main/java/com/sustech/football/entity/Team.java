package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @TableId(type = IdType.AUTO)
    private Long teamId;
    private String name;
    private String logoUrl;
    private Long captainId;
    @TableField(exist = false)
    private List<Coach> coachList;
    @TableField(exist = false)
    private List<Player> playerList;
    @TableField(exist = false)
    private List<Event> eventList;
    @TableField(exist = false)
    private List<Match> matchList;
    @TableField(exist = false)
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
