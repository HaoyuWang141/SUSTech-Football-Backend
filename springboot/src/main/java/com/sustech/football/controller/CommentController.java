package com.sustech.football.controller;

import com.sustech.football.entity.MatchComment;
import com.sustech.football.entity.MatchCommentReply;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.model.comment.VoMatchComment;
import com.sustech.football.model.match.VoMatchCommentLike;
import com.sustech.football.service.MatchCommentReplyService;
import com.sustech.football.service.MatchCommentService;
import com.sustech.football.service.MatchService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "评论比赛的接口")
public class CommentController {
    @Autowired
    private MatchCommentService matchCommentService;

    @Autowired
    private MatchCommentReplyService matchCommentReplyService;

    @Autowired
    private MatchService matchService;

    @PostMapping("/match/addComment")
    @Operation(summary = "评论比赛", description = "提供比赛评论数据结构体")
    public String addMatchComment(@RequestBody MatchComment comment) {
        if (comment == null) {
            throw new BadRequestException("未提供评论参数");
        }
        if (comment.getMatchId() == null || comment.getUserId() == null || comment.getContent() == null) {
            throw new BadRequestException("评论参数缺失");
        }
        if (matchService.getById(comment.getMatchId()) == null) {
            throw new BadRequestException("评论的比赛不存在");
        }
        comment.setTime(null);
        comment.setCommentId(null);
        if (!matchCommentService.save(comment)) {
            throw new InternalServerErrorException("添加比赛评论发生未知错误");
        }
        ;
        return "添加比赛评论成功";
    }

    @PostMapping("/match/deleteComment")
    @Operation(summary = "删除比赛评论", description = "需要提供评论 ID，操作者 ID")
    @Parameters({
            @Parameter(name = "commentId", description = "评论 ID", required = true),
            @Parameter(name = "userId", description = "操作者 ID", required = true)
    })
    public String deleteMatchComment(Long commentId, Long userId) {
        MatchComment comment = matchCommentService.getById(commentId);
        if (comment == null) {
            throw new BadRequestException("要删除的比赛评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new BadRequestException("没有权限删除该比赛评论");
        }
        if (!matchCommentService.removeById(commentId)) {
            return "删除比赛评论发生未知错误";
        }
        return "删除比赛评论成功";
    }

    @PostMapping("/match/addReply")
    @Operation(summary = "回复比赛评论", description = "提供比赛评论回复数据结构体")
    public String addMatchCommentReply(@RequestBody MatchCommentReply reply) {
        if (reply == null) {
            throw new BadRequestException("未提供回复参数");
        }
        if (reply.getCommentId() == null || reply.getUserId() == null || reply.getContent() == null) {
            throw new BadRequestException("回复参数缺失");
        }
        if (matchCommentService.getById(reply.getCommentId()) == null) {
            throw new BadRequestException("要回复的比赛评论不存在");
        }
        reply.setTime(null);
        reply.setReplyId(null);
        if (!matchCommentReplyService.save(reply)) {
            throw new InternalServerErrorException("回复比赛评论发生未知错误");
        }
        return "回复比赛评论成功";
    }

    @PostMapping("/match/deleteReply")
    @Operation(summary = "删除比赛评论", description = "需要提供回复 ID，操作者 ID")
    @Parameters({
            @Parameter(name = "replyId", description = "回复 ID", required = true),
            @Parameter(name = "userId", description = "操作者 ID", required = true)
    })
    public String deleteMatchCommentReply(Long replyId, Long userId) {
        MatchCommentReply reply = matchCommentReplyService.getById(replyId);
        if (reply == null) {
            throw new BadRequestException("要删除的比赛评论回复不存在");
        }
        if (!reply.getUserId().equals(userId)) {
            throw new BadRequestException("没有权限删除该比赛评论回复");
        }
        if (!matchCommentReplyService.removeById(replyId)) {
            return "删除比赛评论回复发生未知错误";
        }
        return "删除比赛评论回复成功";
    }

    @GetMapping("/match/getCommentWithReply")
    @Operation(summary = "列出一个比赛的评论和回复", description = "需要提供比赛 ID，返回该比赛的评论和回复列表，包括用户简要信息")
    @Parameter(name = "matchId", description = "比赛 ID", required = true)
    public List<VoMatchComment> getCommentsWithReplyByMatchId(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("未提供比赛id");
        }
        return matchCommentService.getCommentsWithReplyByMatchId(matchId);
    }

    @PostMapping("/match/like/getByIdList")
    @Operation(summary = "列出评论的点赞情况", description = "需要提供要获取的评论 ID 和当前用户 ID，返回评论的点赞情况")
    @Parameters({
            @Parameter(name = "userId", description = "当前用户 ID", required = true),
            @Parameter(name = "commentIds", description = "需要获取的评论 ID 的列表", required = true)
    })
    public List<VoMatchCommentLike> getCommentLikesByCommentIdList(Long userId, @RequestParam List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            throw new BadRequestException("未提供评论ID");
        }
        if (userId == null) {
            userId = -1L;
        }
        return matchCommentService.getCommentLikesByCommentIdList(userId, commentIds);
    }

    @PostMapping("/match/like/doLike")
    @Operation(summary = "点赞评论", description = "需要提供评论 ID 和用户 ID，点赞成功返回true，失败返回false")
    @Parameters({
            @Parameter(name = "commentId", description = "评论 ID", required = true),
            @Parameter(name = "userId", description = "用户 ID", required = true)
    })
    public boolean likeComment(Long commentId, Long userId) {
        if (userId == null || commentId == null) {
            throw new BadRequestException("未提供用户ID或评论ID");
        }
        if (matchCommentService.hasLiked(commentId, userId)) {
            throw new BadRequestException("用户已经点赞该评论");
        }
        return matchCommentService.likeComment(commentId, userId);
    }

    @PostMapping("/match/like/cancelLike")
    @Operation(summary = "取消点赞评论", description = "需要提供评论 ID 和用户 ID ，取消点赞成功返回true，失败返回false")
    @Parameters({
            @Parameter(name = "commentId", description = "评论 ID", required = true),
            @Parameter(name = "userId", description = "用户 ID", required = true)
    })
    public boolean cancelLikeComment(@RequestParam Long commentId, @RequestParam Long userId) {
        if (userId == null || commentId == null) {
            throw new BadRequestException("未提供用户ID或评论ID");
        }
        if (!matchCommentService.hasLiked(commentId, userId)) {
            throw new BadRequestException("用户未点赞该评论");
        }
        return matchCommentService.cancelLikeComment(commentId, userId);
    }
}
