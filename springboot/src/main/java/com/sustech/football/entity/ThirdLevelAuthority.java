package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdLevelAuthority {

    @TableId(value = "authority_id", type = IdType.AUTO)
    private Long authorityId;

    private Long secondLevelAuthorityId;

    private Long userId;

    private String description;

    private Long createUserId;

    @TableField(exist = false)
    private User user;
}
