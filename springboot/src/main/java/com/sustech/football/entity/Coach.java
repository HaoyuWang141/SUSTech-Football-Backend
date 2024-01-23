package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    @TableId(type = IdType.AUTO)
    private Long coachId;
    private String name;
    private String photoUrl;
    private String bio;
    private Long userId;
    @TableField(exist = false)
    List<Team> teamList;
}
