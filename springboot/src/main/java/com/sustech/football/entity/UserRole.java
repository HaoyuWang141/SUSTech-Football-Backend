package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户职责对象")
public class UserRole {
    @Schema(description = "用户 ID", example = "1")
    private Long userId;

    @Schema(description = "用户头像链接", example = "https://example.com:8085/download?filename=avatar.jpg")
    private String avatarUrl;

    @Schema(description = "用户昵称", example = "张三爱踢球")
    private String nickName;

    @TableField(exist = false)
    @Schema(description = "裁判职责信息")
    private Referee refereeRole;

    @TableField(exist = false)
    @Schema(description = "球员职责信息")
    private Player playerRole;

    @TableField(exist = false)
    @Schema(description = "教练职责信息")
    private Coach coachRole;

    public UserRole(Long userId, String avatarUrl, String nickName) {
        this.userId = userId;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

}

