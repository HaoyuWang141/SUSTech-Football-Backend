package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @TableId(type = IdType.AUTO)
    private Long playerId;
    private String name;
    private String photoUrl;
    private Date birthDate;
    private Integer height;
    private Integer weight;
    private String position;
    private String identity;
    private String shuYuan;
    private String college;
    private Integer admissionYear;
    private String bio;
    private Long userId;
    @TableField(exist = false)
    private List<Team> teamList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerId, player.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}
