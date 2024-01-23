package com.sustech.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @TableId(type = IdType.AUTO)
    private Long matchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Timestamp time;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String liveUrl;
    private String videoUrl;
}
