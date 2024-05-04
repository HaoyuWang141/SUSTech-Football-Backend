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
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private MatchCommentService matchCommentService;

    @Autowired
    private MatchCommentReplyService matchCommentReplyService;

    @Autowired
    private MatchService matchService;

    @PostMapping("/match/addComment")
    @Operation(summary = "评论比赛", description = "需要提供比赛id，评论者id，评论内容")
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
    @Operation(summary = "删除比赛评论", description = "需要提供评论id，操作者id")
    public String deleteMatchComment(@RequestParam Long commentId, @RequestParam Long userId) {
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
    @Operation(summary = "回复比赛评论", description = "需要提供评论id，回复者id，回复内容")
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
    @Operation(summary = "删除比赛评论", description = "需要提供回复id，操作者id")
    public String deleteMatchCommentReply(@RequestParam Long replyId, @RequestParam Long userId) {
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
    @Operation(summary = "列出一个比赛的评论和回复", description = "需要提供比赛id，返回该比赛的评论和回复列表，包括用户简要信息")
    public List<VoMatchComment> getCommentsWithReplyByMatchId(@RequestParam Long matchId) {
        return matchCommentService.getCommentsWithReplyByMatchId(matchId);
    }

    @GetMapping("/match/getCommentLikes")
    @Operation(summary = "列出评论的点赞情况", description = "需要提供要获取的评论id和当前用户id，返回评论的点赞情况")
    public List<VoMatchCommentLike> getCommentLikesByCommentIdList(@RequestParam Long userId, @RequestParam List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            throw new BadRequestException("未提供评论id");
        }
        if (userId == null) {
            userId = -1L;
        }
        return matchCommentService.getCommentLikesByCommentIdList(userId, commentIds);
    }

    @PostMapping("/match/likeComment")
    @Operation(summary = "点赞评论", description = "需要提供评论id和用户id，点赞成功返回true，失败返回false")
    public boolean likeComment(@RequestParam Long commentId, @RequestParam Long userId) {
        if (userId == null || commentId == null) {
            throw new BadRequestException("未提供用户id或评论id");
        }
        if (matchCommentService.hasLiked(commentId, userId)) {
            throw new BadRequestException("用户已经点赞该评论");
        }
        return matchCommentService.likeComment(commentId, userId);
    }

    @PostMapping("/match/cancelLikeComment")
    @Operation(summary = "取消点赞评论", description = "需要提供评论id和用户id，取消点赞成功返回true，失败返回false")
    public boolean cancelLikeComment(@RequestParam Long commentId, @RequestParam Long userId) {
        if (userId == null || commentId == null) {
            throw new BadRequestException("未提供用户id或评论id");
        }
        if (!matchCommentService.hasLiked(commentId, userId)) {
            throw new BadRequestException("用户未点赞该评论");
        }
        return matchCommentService.cancelLikeComment(commentId, userId);
    }
}
