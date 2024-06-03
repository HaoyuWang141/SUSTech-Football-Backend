package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "球队教练对象")
public class TeamCoach {
    @MppMultiId
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @MppMultiId
    @Schema(description = "教练 ID", example = "1")
    private Long coachId;

    @TableField(exist = false)
    @Schema(description = "球队信息")
    private Team team;

    @TableField(exist = false)
    @Schema(description = "教练信息")
    private Coach coach;

    public TeamCoach(Long teamId, Long coachId) {
        this.teamId = teamId;
        this.coachId = coachId;
    }
}
