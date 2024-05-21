package com.sustech.football.model.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMatchCommentLike {
    Long commentId;
    Long userId;
    Long likeNum;
    Boolean hasLiked;
}
