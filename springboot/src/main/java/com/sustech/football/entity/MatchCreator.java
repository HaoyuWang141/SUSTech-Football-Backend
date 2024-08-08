package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchCreator {

    @TableId(value = "match_id")
    private Long matchId;

    private Long userId;

    private Integer createAuthorityLevel;

    private Long createAuthorityId;
}
