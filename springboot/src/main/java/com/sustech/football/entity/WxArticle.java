package com.sustech.football.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("wx_article")
@Schema(description = "微信文章")
public class WxArticle {
    @TableId(type = IdType.AUTO)

    @Schema(description = "文章 ID", example = "1")
    private Long articleId;

    @Schema(description = "文章链接", example = "https://mp.weixin.qq.com/s/xxxxxx")
    private String url;

    @Schema(description = "封面图片链接", example = "https://example.com:8085/cover.jpg")
    private String cover_url;

    @Schema(description = "文章标题", example = "标题")
    private String title;

    @Schema(description = "文章时间", example = "2024-07-01 12:00:00")
    private Timestamp time;
}
