package com.sustech.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchPlayerAction {
    @MppMultiId
    private Long match_id;
    @MppMultiId
    private Long team_id;
    @MppMultiId
    private Long player_id;
    @MppMultiId
    private String action;
    @MppMultiId
    private Time time;
}