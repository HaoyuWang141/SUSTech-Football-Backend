package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreator {

    @TableId(value = "team_id")
    private Integer teamId;

    private Integer userId;

    private Integer createAuthorityLevel;

    private Integer createAuthorityId;
}
