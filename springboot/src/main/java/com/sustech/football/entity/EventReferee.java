package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "赛事裁判对象")
public class EventReferee {
    @MppMultiId
    @Schema(description = "赛事 ID", example = "1")
    private Long eventId;

    @MppMultiId
    @Schema(description = "裁判 ID", example = "1")
    private Long refereeId;

    @TableField(exist = false)
    @Schema(description = "赛事信息")
    private Event event;

    @TableField(exist = false)
    @Schema(description = "裁判信息")
    private Referee referee;

    public EventReferee(Long eventId, Long refereeId) {
        this.eventId = eventId;
        this.refereeId = refereeId;
    }
}
