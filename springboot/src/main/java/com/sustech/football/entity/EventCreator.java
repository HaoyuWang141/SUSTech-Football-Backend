package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreator {

    @TableId(value = "event_id")
    private Long eventId;

    private Long userId;

    private Integer createAuthorityLevel;

    private Long createAuthorityId;
}
