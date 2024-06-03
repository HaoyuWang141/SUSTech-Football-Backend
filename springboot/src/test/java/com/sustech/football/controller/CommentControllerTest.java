package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.model.match.VoMatchCommentLike;
import com.sustech.football.service.MatchCommentReplyService;
import com.sustech.football.service.MatchCommentService;
import com.sustech.football.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */

class CommentControllerTest {

    @Mock
    private MatchCommentService matchCommentService;

    @Mock
    private MatchCommentReplyService matchCommentReplyService;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addMatchComment_ValidComment_ReturnsSuccessMessage() {
        MatchComment comment = new MatchComment();
        comment.setMatchId(1L);
        comment.setUserId(2L);
        comment.setContent("Great match!");

        when(matchService.getById(comment.getMatchId())).thenReturn(new Match());
        when(matchCommentService.save(any(MatchComment.class))).thenReturn(true);

        String result = commentController.addMatchComment(comment);

        assertEquals("添加比赛评论成功", result);
    }

    @Test
    void addMatchComment_NullComment_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> commentController.addMatchComment(null));
    }

    @Test
    void addMatchComment_MissingFields_ThrowsBadRequestException() {
        MatchComment comment = new MatchComment();
        assertThrows(BadRequestException.class, () -> commentController.addMatchComment(comment));
    }

    @Test
    void addMatchComment_NonexistentMatch_ThrowsBadRequestException() {
        MatchComment comment = new MatchComment();
        comment.setMatchId(1L);
        comment.setUserId(2L);
        comment.setContent("Great match!");

        when(matchService.getById(comment.getMatchId())).thenReturn(null);

        assertThrows(BadRequestException.class, () -> commentController.addMatchComment(comment));
    }

    @Test
    void addMatchComment_SaveError_ThrowsInternalServerErrorException() {
        MatchComment comment = new MatchComment();
        comment.setMatchId(1L);
        comment.setUserId(2L);
        comment.setContent("Great match!");

        when(matchService.getById(comment.getMatchId())).thenReturn(new Match());
        when(matchCommentService.save(any(MatchComment.class))).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> commentController.addMatchComment(comment));
    }

    @Test
    void deleteMatchComment_ValidComment_ReturnsSuccessMessage() {
        MatchComment comment = new MatchComment();
        comment.setUserId(1L);

        when(matchCommentService.getById(1L)).thenReturn(comment);
        when(matchCommentService.removeById(1L)).thenReturn(true);

        String result = commentController.deleteMatchComment(1L, 1L);

        assertEquals("删除比赛评论成功", result);
    }

    @Test
    void deleteMatchComment_NonexistentComment_ThrowsBadRequestException() {
        when(matchCommentService.getById(1L)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> commentController.deleteMatchComment(1L, 1L));
    }

    @Test
    void deleteMatchComment_NoPermission_ThrowsBadRequestException() {
        MatchComment comment = new MatchComment();
        comment.setUserId(2L);

        when(matchCommentService.getById(1L)).thenReturn(comment);

        assertThrows(BadRequestException.class, () -> commentController.deleteMatchComment(1L, 1L));
    }

    @Test
    void deleteMatchComment_RemoveError_ReturnsErrorMessage() {
        MatchComment comment = new MatchComment();
        comment.setUserId(1L);

        when(matchCommentService.getById(1L)).thenReturn(comment);
        when(matchCommentService.removeById(1L)).thenReturn(false);

        String result = commentController.deleteMatchComment(1L, 1L);

        assertEquals("删除比赛评论发生未知错误", result);
    }

    @Test

    void addMatchCommentReply_ValidReply_ReturnsSuccessMessage() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setCommentId(1L);
        reply.setUserId(2L);
        reply.setContent("Great match!");

        when(matchCommentService.getById(reply.getCommentId())).thenReturn(new MatchComment());
        when(matchCommentReplyService.save(any(MatchCommentReply.class))).thenReturn(true);

        String result = commentController.addMatchCommentReply(reply);

        assertEquals("回复比赛评论成功", result);
    }

    @Test
    void addMatchCommentReply_NullReply_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> commentController.addMatchCommentReply(null));
    }

    @Test
    void addMatchCommentReply_MissingFields_ThrowsBadRequestException() {
        MatchCommentReply reply = new MatchCommentReply();
        assertThrows(BadRequestException.class, () -> commentController.addMatchCommentReply(reply));
    }

    @Test
    void addMatchCommentReply_NonexistentComment_ThrowsBadRequestException() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setCommentId(1L);
        reply.setUserId(2L);
        reply.setContent("Great match!");

        when(matchCommentService.getById(reply.getCommentId())).thenReturn(null);

        assertThrows(BadRequestException.class, () -> commentController.addMatchCommentReply(reply));
    }

    @Test
    void addMatchCommentReply_SaveError_ThrowsInternalServerErrorException() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setCommentId(1L);
        reply.setUserId(2L);
        reply.setContent("Great match!");

        when(matchCommentService.getById(reply.getCommentId())).thenReturn(new MatchComment());
        when(matchCommentReplyService.save(any(MatchCommentReply.class))).thenReturn(false);

        assertThrows(InternalServerErrorException.class, () -> commentController.addMatchCommentReply(reply));
    }

    @Test
    void deleteMatchCommentReply_ValidReply_ReturnsSuccessMessage() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setUserId(1L);

        when(matchCommentReplyService.getById(1L)).thenReturn(reply);
        when(matchCommentReplyService.removeById(1L)).thenReturn(true);

        String result = commentController.deleteMatchCommentReply(1L, 1L);

        assertEquals("删除比赛评论回复成功", result);
    }

    @Test
    void deleteMatchCommentReply_NonexistentReply_ThrowsBadRequestException() {
        when(matchCommentReplyService.getById(1L)).thenReturn(null);

        assertThrows(BadRequestException.class, () -> commentController.deleteMatchCommentReply(1L, 1L));
    }

    @Test
    void deleteMatchCommentReply_NoPermission_ThrowsBadRequestException() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setUserId(2L);

        when(matchCommentReplyService.getById(1L)).thenReturn(reply);

        assertThrows(BadRequestException.class, () -> commentController.deleteMatchCommentReply(1L, 1L));
    }

    @Test
    void deleteMatchCommentReply_RemoveError_ReturnsErrorMessage() {
        MatchCommentReply reply = new MatchCommentReply();
        reply.setUserId(1L);

        when(matchCommentReplyService.getById(1L)).thenReturn(reply);
        when(matchCommentReplyService.removeById(1L)).thenReturn(false);

        String result = commentController.deleteMatchCommentReply(1L, 1L);

        assertEquals("删除比赛评论回复发生未知错误", result);
    }

    @Test
    void likeComment_ValidComment_ReturnsSuccessMessage() {
        when(matchCommentService.likeComment(1L, 1L)).thenReturn(true);

        boolean result = commentController.likeComment(1L, 1L);

        assertTrue(result);
    }

    @Test
    void likeComment_ValidParams_ReturnsTrue() {
        Long commentId = 1L;
        Long userId = 1L;
        when(matchCommentService.hasLiked(commentId, userId)).thenReturn(false);
        when(matchCommentService.likeComment(commentId, userId)).thenReturn(true);

        boolean result = commentController.likeComment(commentId, userId);

        assertTrue(result);
    }

    @Test
    void likeComment_NullUserId_ThrowsBadRequestException() {
        Long commentId = 1L;
        Long userId = null;

        assertThrows(BadRequestException.class, () -> commentController.likeComment(commentId, userId));
    }

    @Test
    void likeComment_NullCommentId_ThrowsBadRequestException() {
        Long commentId = null;
        Long userId = 1L;

        assertThrows(BadRequestException.class, () -> commentController.likeComment(commentId, userId));
    }

    @Test
    void likeComment_AlreadyLiked_ThrowsBadRequestException() {
        Long commentId = 1L;
        Long userId = 1L;
        when(matchCommentService.hasLiked(commentId, userId)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> commentController.likeComment(commentId, userId));
    }

    @Test
    void cancelLikeComment_ValidParams_ReturnsTrue() {
        Long commentId = 1L;
        Long userId = 1L;
        when(matchCommentService.hasLiked(commentId, userId)).thenReturn(true);
        when(matchCommentService.cancelLikeComment(commentId, userId)).thenReturn(true);

        boolean result = commentController.cancelLikeComment(commentId, userId);

        assertTrue(result);
    }

    @Test
    void cancelLikeComment_NullUserId_ThrowsBadRequestException() {
        Long commentId = 1L;
        Long userId = null;

        assertThrows(BadRequestException.class, () -> commentController.cancelLikeComment(commentId, userId));
    }

    @Test
    void cancelLikeComment_NullCommentId_ThrowsBadRequestException() {
        Long commentId = null;
        Long userId = 1L;

        assertThrows(BadRequestException.class, () -> commentController.cancelLikeComment(commentId, userId));
    }

    @Test
    void cancelLikeComment_NotLiked_ThrowsBadRequestException() {
        Long commentId = 1L;
        Long userId = 1L;
        when(matchCommentService.hasLiked(commentId, userId)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> commentController.cancelLikeComment(commentId, userId));
    }

    @Test
    void getCommentLikesByCommentIdList_NullCommentIds_ThrowsBadRequestException() {
        Long userId = 1L;
        List<Long> commentIds = null;

        assertThrows(BadRequestException.class, () -> commentController.getCommentLikesByCommentIdList(userId, commentIds));
    }

    @Test
    void getCommentLikesByCommentIdList_NullUserId_ReturnsLikes() {
        Long userId = null;
        List<Long> commentIds = new ArrayList<>();
        commentIds.add(1L);
        commentIds.add(2L);
        when(matchCommentService.getCommentLikesByCommentIdList(-1L, commentIds)).thenReturn(new ArrayList<>());

        List<VoMatchCommentLike> result = commentController.getCommentLikesByCommentIdList(userId, commentIds);

        assertNotNull(result);
    }

    @Test
    void getCommentsWithReplyByMatchId_NullMatchId_ThrowsBadRequestException() throws Exception {
        Long matchId = null;

        assertThrows(BadRequestException.class, () -> commentController.getCommentsWithReplyByMatchId(matchId));
    }

}
