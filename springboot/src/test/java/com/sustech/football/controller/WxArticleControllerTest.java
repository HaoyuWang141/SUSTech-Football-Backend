package com.sustech.football.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sustech.football.service.*;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;
import java.util.List;

/**
 * AI-generated-content
 * tool: ChatGPT
 * version: 3.5 turbo
 * usage: I give it the class and method implementation it writes the tests for me.
 */


public class WxArticleControllerTest {

    @Mock
    private WxArticleService wxArticleService;

    @InjectMocks
    private WxArticleController wxArticleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd_NullArticle_ThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> wxArticleController.add(null));
    }

    @Test
    public void testAdd_ArticleWithId_ThrowsBadRequestException() {
        WxArticle article = new WxArticle();
        article.setArticleId(1L);
        assertThrows(BadRequestException.class, () -> wxArticleController.add(article));
    }

    @Test
    public void testAdd_MissingTitleOrUrl_ThrowsBadRequestException() {
        WxArticle article = new WxArticle();
        assertThrows(BadRequestException.class, () -> wxArticleController.add(article));
        article.setUrl("http://example.com");
        assertThrows(BadRequestException.class, () -> wxArticleController.add(article));
        article.setUrl(null);
        article.setTitle("Some Title");
        assertThrows(BadRequestException.class, () -> wxArticleController.add(article));
    }

    @Test
    public void testAdd_ValidArticle_Success() {
        WxArticle article = new WxArticle();
        article.setUrl("http://example.com");
        article.setTitle("Title");
        wxArticleController.add(article);
        verify(wxArticleService).save(any(WxArticle.class));
    }

    @Test
    public void testGetLatestArticles_Success() {
        when(wxArticleService.getLatestArticles()).thenReturn(Arrays.asList(new WxArticle(), new WxArticle()));
        List<WxArticle> articles = wxArticleController.getLatestArticles();
        assertEquals(2, articles.size());
    }

    @Test
    public void testGetAll_Success() {
        when(wxArticleService.list()).thenReturn(Arrays.asList(new WxArticle(), new WxArticle()));
        List<WxArticle> articles = wxArticleController.getAll();
        assertEquals(2, articles.size());
    }
}