package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGroup {
    @TableId(type = IdType.AUTO)
    private Long groupId;
    private Long eventId;
    private String name;
    @TableField(exist = false)
    private Event event;
    @TableField(exist = false)
    private List<Team> teams;

    public EventGroup(Long eventId, String name) {
        this.eventId = eventId;
        this.name = name;
    }

    public EventGroup(Long groupId, Long eventId, String name) {
        this.groupId = groupId;
        this.eventId = eventId;
        this.name = name;
    }
}
