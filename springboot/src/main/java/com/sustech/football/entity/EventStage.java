package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("event_stage")
public class EventStage {
    public static final String STAGE_CUP_GROUP = "小组赛";
    public static final String STAGE_CUP_KNOCKOUT = "淘汰赛";
    public static final String STAGE_LEAGUE = "联赛";

    @MppMultiId
    private Long eventId;
    @MppMultiId
    private String stage;
}
