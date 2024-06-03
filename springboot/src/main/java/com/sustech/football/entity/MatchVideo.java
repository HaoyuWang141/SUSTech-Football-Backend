package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛录像对象")
public class MatchVideo {
    @TableId(type = IdType.AUTO)
    @Schema(description = "录像 ID", example = "1")
    private Long videoId;

    @Schema(description = "比赛 ID", example = "1")
    private Long matchId;

    @Schema(description = "录像名称", example = "书院杯录像")
    private String videoName;

    @Schema(description = "录像地址", example = "www.example.com/video?id=111")
    private String videoUrl;
}
