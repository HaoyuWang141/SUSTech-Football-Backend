package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTeam {
    @MppMultiId
    private Long eventId;
    @MppMultiId
    private Long teamId;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
    private Team team;

    public EventTeam(Long eventId, Long teamId) {
        this.eventId = eventId;
        this.teamId = teamId;
    }
}
