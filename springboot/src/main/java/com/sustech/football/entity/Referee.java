package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "裁判身份对象")
public class Referee {
    @TableId(type = IdType.AUTO)
    @Schema(description = "裁判 ID", example = "1")
    private Long refereeId;

    @Schema(description = "裁判姓名", example = "王五")
    private String name;

    @Schema(description = "裁判头像链接", example = "www.example.com:8085/download?filename=wangwu_photo.png")
    private String photoUrl;

    @Schema(description = "裁判简介", example = "我是王五，我是一名裁判")
    private String bio;

    @Schema(description = "裁判用户 ID", example = "1")
    private Long userId;

    @TableField(exist = false)
    @Schema(description = "裁判执法比赛列表")
    private List<Match> matchList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referee referee = (Referee) o;
        return refereeId.equals(referee.refereeId);
    }

    @Override
    public int hashCode() {
        return refereeId.hashCode();
    }
}

