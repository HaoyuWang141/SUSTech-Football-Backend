package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "赛事分组对象")
public class EventGroup {
    @TableId(type = IdType.AUTO)
    @Schema(description = "赛事分组 ID", example = "1")
    private Long groupId;

    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @Schema(description = "分组名称", example = "A 组")
    private String name;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "分组球队列表")
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
