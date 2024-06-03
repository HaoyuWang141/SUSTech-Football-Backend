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
@Schema(description = "比赛裁判对象")
public class MatchReferee {
    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "裁判 ID", example = "1")
    private Long refereeId;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    @TableField(exist = false)
    @Schema(description = "裁判信息")
    private Referee referee;

    public MatchReferee(Long matchId, Long refereeId) {
        this.matchId = matchId;
        this.refereeId = refereeId;
    }
}
