package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("match_comment_reply")
public class MatchCommentReply {
    @TableId(type = IdType.AUTO)
    private Long replyId;
    private Long userId;
    private Timestamp time;

    private Long commentId;
    private String content;
}
