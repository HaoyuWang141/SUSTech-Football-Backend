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
@Schema(description = "赛事比赛对象")
public class EventMatch {
    public static final String STAGE_CUP_GROUP = "小组赛";
    public static final String STAGE_CUP_KNOCKOUT = "淘汰赛";
    public static final String STAGE_LEAGUE = "联赛";

    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @Schema(description = "阶段", example = "小组赛")
    private String stage;

    @Schema(description = "标签", example = "A 组")
    private String tag;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "比赛信息")
    private Match match;

    public EventMatch(Long eventId, Long matchId) {
        this.eventId = eventId;
        this.matchId = matchId;
    }

    public EventMatch(Long eventId, Long matchId, String stage, String tag) {
        this.eventId = eventId;
        this.matchId = matchId;
        this.stage = stage;
        this.tag = tag;
    }
}
