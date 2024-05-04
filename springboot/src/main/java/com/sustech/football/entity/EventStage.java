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
    @MppMultiId
    @TableField(value = "event_id")
    private Long eventId;

    @MppMultiId
    @TableField(value = "stage")
    private String stage;
}
