package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchLive {
    @TableId(type = IdType.AUTO)
    private Long liveId;
    private Long matchId;
    private String liveName;
    private String liveUrl;
}
