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
@Schema(description = "赛事队伍对象")
public class EventTeam {
    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "队伍 ID", example = "1")
    private Long teamId;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "队伍信息")
    private Team team;

    public EventTeam(Long eventId, Long teamId) {
        this.eventId = eventId;
        this.teamId = teamId;
    }
}
