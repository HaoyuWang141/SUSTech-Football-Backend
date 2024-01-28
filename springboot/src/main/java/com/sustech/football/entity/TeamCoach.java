package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCoach {
    @MppMultiId
    private Long teamId;
    @MppMultiId
    private Long coachId;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Coach coach;

    public TeamCoach(Long teamId, Long coachId) {
        this.teamId = teamId;
        this.coachId = coachId;
    }
}
