package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondLevelAuthority {

    @TableId(value = "authority_id", type = IdType.AUTO)
    private Long authorityId;

    private String username;

    private String password;

    private String description;

    private Long createUserId;
}
