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
public class UserRole {
    private Long userId;
    private String avatarUrl;
    private String nickName;
    @TableField(exist = false)
    private Referee refereeRole;
    @TableField(exist = false)
    private Player playerRole;
    @TableField(exist = false)
    private Coach coachRole;

    public UserRole(Long userId, String avatarUrl, String nickName) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

}

