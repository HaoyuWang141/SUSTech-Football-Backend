package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("event_stage")
@Schema(description = "赛事阶段对象")
public class EventStage {
    public static final String STAGE_CUP_GROUP = "小组赛";
    public static final String STAGE_CUP_KNOCKOUT = "淘汰赛";
    public static final String STAGE_LEAGUE = "联赛";

    @MppMultiId
    @TableField(value = "event_id")
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @TableField(value = "stage")
    @Schema(description = "阶段", example = "小组赛")
    private String stage;
}
