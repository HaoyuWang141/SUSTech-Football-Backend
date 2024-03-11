package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String openid;
    private String sessionKey;
    private String avatarUrl;
    private String nickName;

    public User(String openid, String sessionKey) {
        this.openid = openid;
        this.sessionKey = sessionKey;
    }

    public User(Long userId, String avatarUrl, String nickName) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

    public User(String openid, String sessionKey, String avatarUrl, String nickName) {
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

}

