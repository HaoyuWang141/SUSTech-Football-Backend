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
@Schema(description = "比赛管理员对象")
public class MatchManager {
    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @MppMultiId
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @Schema(description = "是否为比赛创建者", example = "true")
    private Boolean isOwner;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    public MatchManager(Long matchId, Long userId, Boolean isOwner) {
        this.matchId = matchId;
        this.userId = userId;
        this.isOwner = isOwner;
    }
}
