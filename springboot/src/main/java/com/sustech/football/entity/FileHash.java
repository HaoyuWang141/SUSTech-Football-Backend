package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件哈希对象")
public class FileHash {
    @TableId
    @Schema(description = "主键", example = "1")
    private Long fileId;

    @Schema(description = "文件哈希值", example = "d41d8cd98f00b204e9800998ecf8427e")
    private String hash;

    @Schema(description = "文件名", example = "example.txt")
    private String fileName;
}
