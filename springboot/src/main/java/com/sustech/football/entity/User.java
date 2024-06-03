package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
@Schema(description = "用户对象")
public class User {
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @Schema(description = "用户 openid", example = "x122x1")
    private String openid;

    @Schema(description = "用户 sessionKey", example = "x1x1x1")
    private String sessionKey;

    @Schema(description = "用户头像链接", example = "https://example.com:8085/download?filename=avatar.jpg")
    private String avatarUrl;

    @Schema(description = "用户昵称", example = "张三爱踢球")
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

