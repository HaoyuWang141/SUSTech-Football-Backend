package com.sustech.football.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("match_comment_like")
public class MatchCommentLike {
    private Long commentId;
    private Long userId;
}
