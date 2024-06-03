package com.sustech.football.controller;

import com.sustech.football.exception.BadRequestException;
import com.sustech.football.service.UserFavoriteService;
import com.sustech.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "User Favorite Controller", description = "用户收藏管理接口")
public class UserFavoriteController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserService userService, UserFavoriteService userFavoriteService) {
        this.userService = userService;
        this.userFavoriteService = userFavoriteService;
    }

    @PostMapping("/favorite")
    @Operation(summary = "收藏", description = "提供收藏用户 ID，类型和收藏 ID，收藏")
    @Parameters({
            @Parameter(name = "userId", description = "用户 ID", required = true),
            @Parameter(name = "type", description = "收藏类型，类型可选：team, user, match, event", required = true),
            @Parameter(name = "id", description = "收藏 ID", required = true)
    })
    public void favorite(Long userId, String type, Long id) {
        if (userId == null || type == null || id == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        switch (type) {
            case "team" -> userFavoriteService.favoriteTeam(userId, id);
            case "user" -> userFavoriteService.favoriteUser(userId, id);
            case "match" -> userFavoriteService.favoriteMatch(userId, id);
            case "event" -> userFavoriteService.favoriteEvent(userId, id);
            default -> throw new BadRequestException("参数错误");
        }
    }

    @PostMapping("/unfavorite")
    @Operation(summary = "取消收藏", description = "提供收藏用户 ID，类型和收藏 ID，取消收藏")
    @Parameters({
            @Parameter(name = "userId", description = "用户 ID", required = true),
            @Parameter(name = "type", description = "收藏类型，类型可选：team, user, match, event", required = true),
            @Parameter(name = "id", description = "收藏 ID", required = true)
    })
    public void unfavorite(Long userId, String type, Long id) {
        if (userId == null || type == null || id == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        switch (type) {
            case "team" -> userFavoriteService.unfavoriteTeam(userId, id);
            case "user" -> userFavoriteService.unfavoriteUser(userId, id);
            case "match" -> userFavoriteService.unfavoriteMatch(userId, id);
            case "event" -> userFavoriteService.unfavoriteEvent(userId, id);
            default -> throw new BadRequestException("参数错误");
        }
    }

    @GetMapping("/getFavorite")
    @Operation(summary = "获取收藏", description = "提供收藏用户 ID，类型，获取收藏列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户 ID", required = true),
            @Parameter(name = "type", description = "收藏类型，类型可选：team, user, match, event", required = true)
    })
    public List<?> getFavorite(Long userId, String type) {
        if (userId == null || type == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        return switch (type) {
            case "team" -> userFavoriteService.getFavoriteTeams(userId);
            case "user" -> userFavoriteService.getFavoriteUsers(userId);
            case "match" -> userFavoriteService.getFavoriteMatches(userId);
            case "event" -> userFavoriteService.getFavoriteEvents(userId);
            default -> throw new BadRequestException("参数错误");
        };
    }

    @GetMapping("/isFavorite")
    @Operation(summary = "是否收藏", description = "提供收藏用户 ID，类型和收藏 ID，判断是否收藏")
    @Parameters({
            @Parameter(name = "userId", description = "用户 ID", required = true),
            @Parameter(name = "type", description = "收藏类型，类型可选：team, user, match, event", required = true),
            @Parameter(name = "id", description = "收藏 ID", required = true)
    })
    public boolean isFavorite(Long userId, String type, Long id) {
        if (userId == null || type == null || id == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        return switch (type) {
            case "team" -> userFavoriteService.isFavoriteTeam(userId, id);
            case "user" -> userFavoriteService.isFavoriteUser(userId, id);
            case "match" -> userFavoriteService.isFavoriteMatch(userId, id);
            case "event" -> userFavoriteService.isFavoriteEvent(userId, id);
            default -> throw new BadRequestException("参数错误");
        };
    }
}
