package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchVideo {
    @TableId(type = IdType.AUTO)
    private Long videoId;
    private Long matchId;
    private String videoName;
    private String videoUrl;
}
