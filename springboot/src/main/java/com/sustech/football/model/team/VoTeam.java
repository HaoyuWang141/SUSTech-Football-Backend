package com.sustech.football.model.team;

import com.sustech.football.entity.Coach;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "球队 DTO")
public class VoTeam {
    @Data
    @NoArgsConstructor
    @Schema(description = "用户 DTO")
    public static class VoUser {
        @Schema(description = "用户 ID", example = "1")
        private Long userId;

        @Schema(description = "用户头像链接", example = "www.example.com:8085/download?filename=avatar.png")
        private String avatarUrl;

        @Schema(description = "用户昵称", example = "张三爱踢球")
        private String nickName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "球员 DTO")
    public static class VoPlayer {
        @Schema(description = "球员 ID", example = "1")
        private Long playerId;

        @Schema(description = "球员姓名", example = "李四")
        private String name;

        @Schema(description = "球员头像链接", example = "www.example.com:8085/download?filename=avatar.png")
        private String photoUrl;

        @Schema(description = "球员号码", example = "10")
        private Integer number;

        @Schema(description = "球员位置", example = "前锋")
        private Integer appearances;

        @Schema(description = "球员进球数", example = "3")
        private Integer goals;

        @Schema(description = "球员助攻数", example = "2")
        private Integer assists;

        @Schema(description = "球员黄牌数", example = "2")
        private Integer yellowCards;

        @Schema(description = "球员红牌数", example = "1")
        private Integer redCards;
    }


    
    @Schema(description = "球队 ID", example = "1")
    private Long teamId;

    @Schema(description = "球队名称", example = "致新书院1队")
    private String name;

    @Schema(description = "球队logo链接", example = "www.example.com:8085/download?filename=zhixin_logo.png")
    private String logoUrl;

    @Schema(description = "球队简介", example = "我们是致新书院1队")
    private String description;

    @Schema(description = "队长 ID", example = "1")
    private Long captainId;

    @Schema(description = "球队教练列表")
    private List<Coach> coachList;

    @Schema(description = "球队球员列表")
    private List<VoPlayer> playerList;

    @Schema(description = "球队赛事列表")
    private List<Event> eventList;

    @Schema(description = "球队比赛列表")
    private List<Match> matchList;

    @Schema(description = "球队管理员用户列表")
    private List<VoUser> managerList;
}
