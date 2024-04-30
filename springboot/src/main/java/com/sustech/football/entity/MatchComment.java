package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("match_comment")
public class MatchComment {
    @TableId(type = IdType.AUTO)
    private Long commentId;
    private Long matchId;
    private Long userId;
    private String content;
    private Timestamp time;

}
