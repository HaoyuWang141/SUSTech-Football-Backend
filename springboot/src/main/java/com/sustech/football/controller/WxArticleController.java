package com.sustech.football.controller;


import com.sustech.football.entity.WxArticle;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.service.WxArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/wxArticle")
@Tag(name = "WxArticle", description = "微信文章")
public class WxArticleController {
    @Autowired
    private WxArticleService wxArticleService;

    @PostMapping("/add")
    @Operation(summary = "添加文章", description = "添加微信文章")
    public void add(@RequestBody WxArticle wxArticle) {
        if (wxArticle == null) {
            throw new BadRequestException("参数错误");
        }
        if (wxArticle.getArticleId() != null) {
            throw new BadRequestException("文章 ID 不能指定");
        }
        if (wxArticle.getUrl() == null || wxArticle.getTitle() == null) {
            throw new BadRequestException("URL和标题不能为空");
        }
        wxArticleService.save(wxArticle);
    }

    @GetMapping("/latestArticles")
    @Operation(summary = "最新文章", description = "获取最新的 10 个微信文章")
    public List<WxArticle> getLatestArticles() {
        return wxArticleService.getLatestArticles();
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有文章", description = "获取所有微信文章")
    public List<WxArticle> getAll() {
        return wxArticleService.list();
    }

}
