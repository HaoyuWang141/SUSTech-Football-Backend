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
    private Integer eventId;

    private Integer userId;

    private Integer createAuthorityLevel;

    private Integer createAuthorityId;
}
