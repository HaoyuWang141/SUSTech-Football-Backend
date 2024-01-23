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
public class Event {
    @TableId(type = IdType.AUTO)
    private Long eventId;
    private String name;
    private String description;
    @TableField(exist = false)
    private List<User> managerList;
}
