package com.sustech.football.controller;

import com.sustech.football.exception.BadRequestException;
import com.sustech.football.service.UserFavoriteService;
import com.sustech.football.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class UserFavoriteController {
    private UserService userService;

    private UserFavoriteService userFavoriteService;

    public UserFavoriteController(UserService userService, UserFavoriteService userFavoriteService) {
        this.userService = userService;
        this.userFavoriteService = userFavoriteService;
    }

    @PostMapping("/favorite")
    @Operation(summary = "收藏", description = "type类型可选：team, user, match, event")
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
    @Operation(summary = "取消收藏", description = "type类型可选：team, user, match, event")
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
    @Operation(summary = "获取收藏", description = "type类型可选：team, user, match, event")
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
}
