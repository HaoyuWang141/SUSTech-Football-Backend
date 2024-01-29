package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPlayerRequest {
    public static final String TYPE_APPLICATION = "APPLICATION";
    public static final String TYPE_INVITATION = "INVITATION";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACCEPTED = "ACCEPTED";
    public static final String STATUS_REJECTED = "REJECTED";

    @MppMultiId
    private Long teamId;
    @MppMultiId
    private Long playerId;
    @MppMultiId
    private String type;
    private String status;
    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Player player;

    public TeamPlayerRequest(Long teamId, Long playerId, String type, String status) {
        this.teamId = teamId;
        this.playerId = playerId;
        this.type = type;
        this.status = status;
    }
}
