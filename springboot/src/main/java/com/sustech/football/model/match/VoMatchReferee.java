package com.sustech.football.model.match;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "比赛裁判 DTO")
public class VoMatchReferee {
    @Schema(description = "裁判 ID", example = "1")
    Long refereeId;

    @Schema(description = "裁判姓名", example = "张三")
    String name;

    @Schema(description = "裁判头像 URL", example = "https://example.com/photo.jpg")
    String photoUrl;
}
