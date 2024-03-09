package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Data
    @NoArgsConstructor
    public static class Team {
        public Team(Long id, String name, String logo, Integer playerCount) {
            this.id = id;
            this.name = name;
            this.logo = logo;
            this.playerCount = playerCount;
        }

        Long id;
        String name;
        String logo;
        Integer playerCount;
    }

    @Data
    @NoArgsConstructor
    public static class Group {
        @Data
        @NoArgsConstructor
        public static class Team {
            Event.Team team;
            int numWins;
            int numDraws;
            int numLosses;
            int numGoalsFor;
            int numGoalsAgainst;
            int score;
        }

        Long groupId;
        String name;
        List<Team> teamList;
    }

    @Data
    @NoArgsConstructor
    public static class Tag {
        String tagName;
        List<Match> matches;
    }

    @Data
    @NoArgsConstructor
    public static class Stage {
        String stageName;
        List<Tag> tags;
    }

    @TableId(type = IdType.AUTO)
    private Long eventId;
    private String name;
    private String description;
    @TableField(exist = false)
    private List<Long> managerList;
    @TableField(exist = false)
    List<Event.Team> teamList; // 参加的所有球队
    @TableField(exist = false)
    List<Event.Group> groupList; // 小组赛分组，包括组别名称和小组内球队信息
    @TableField(exist = false)
    List<Match> matchList; // 所有比赛列表，按照时间顺序排列
    @TableField(exist = false)
    List<Stage> stageList; // 比赛阶段，包括阶段名称和二级目录“标签”，标签包括标签名称和比赛列表
}
