package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @TableId(type = IdType.AUTO)
    private Long playerId;
    private String name;
    private String photoUrl;
    private Date birthDate;
    private Integer height;
    private Integer weight;
    private String position;
    private String identity;
    private String shuYuan;
    private String college;
    private Integer admissionYear;
    private String bio;
    private Long userId;
    @TableField(exist = false)
    private List<Team> teamList;
}
