package com.sustech.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @TableId(type = IdType.AUTO)
    private Long teamId;
    private String name;
    private String logoUrl;
    private Long captainId;
}
