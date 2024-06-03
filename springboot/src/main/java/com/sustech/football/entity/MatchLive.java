package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛直播对象")
public class MatchLive {
    @TableId(type = IdType.AUTO)
    @Schema(description = "直播 ID", example = "1")
    private Long liveId;

    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @Schema(description = "直播名称", example = "书院杯直播")
    private String liveName;

    @Schema(description = "直播地址", example = "www.example.com/live?id=111")
    private String liveUrl;
}
