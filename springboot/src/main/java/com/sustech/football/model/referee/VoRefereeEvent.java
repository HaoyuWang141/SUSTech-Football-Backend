package com.sustech.football.model.referee;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "裁判赛事DTO")
public class VoRefereeEvent {
    @Schema(description = "赛事 ID", example = "1")
    Long eventId;

    @Schema(description = "赛事名称", example = "2024年书院杯")
    String eventName;

    @Schema(description = "赛事阶段", example = "小组赛")
    String stage;

    @Schema(description = "赛事标签", example = "A组")
    String tag;
}
