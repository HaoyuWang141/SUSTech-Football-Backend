package com.sustech.football.entity;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStageTag {
    @MppMultiId
    private Long eventId;
    @MppMultiId
    private String stage;
    @MppMultiId
    private String tag;
}
