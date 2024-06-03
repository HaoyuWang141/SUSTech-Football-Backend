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
@Schema(description = "教练对象")
public class Coach {
    @TableId(type = IdType.AUTO)
    @Schema(description = "教练 ID", example = "1")
    private Long coachId;

    @Schema(description = "教练姓名", example = "张三")
    private String name;

    @Schema(description = "教练头像 URL", example = "https://example.com:8085/download?filename=zhangsan.png")
    private String photoUrl;

    @Schema(description = "教练简介", example = "张三是一名优秀的教练")
    private String bio;

    @Schema(description = "教练所属用户 ID", example = "1")
    private Long userId;

    @TableField(exist = false)
    @Schema(description = "教练所属球队列表")
    List<Team> teamList;
}
