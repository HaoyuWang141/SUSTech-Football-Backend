package com.sustech.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referee {
    @TableId(type = IdType.AUTO)
    private Long refereeId;
    private String name;
    private String photoUrl;
    private String bio;
    private Long userId;
}

