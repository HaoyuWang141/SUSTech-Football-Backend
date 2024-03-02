package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.football.entity.User;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.exception.UnauthorizedAccessException;
import com.sustech.football.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestHeader(value = "Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            String[] values = credentials.split(":", 2);

            String username = values[0];
            String password = values[1];

            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("username", username);
            User user = userService.getOne(queryWrapper);
            if (user == null || user.getPassword() == null) {
                throw new UnauthorizedAccessException("用户名或密码错误");
            }
            if (!user.getPassword().equals(password)) {
                throw new UnauthorizedAccessException("用户名或密码错误");
            }
            return ResponseEntity.ok().body(user.getUserId());
        }

        throw new UnauthorizedAccessException("用户名或密码错误");
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(String username, String password) {
        if (username == null || password == null) {
            throw new BadRequestException("用户名和密码不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            throw new ConflictException("用户名已存在");
        }
        user = new User(null, username, password);
        if (userService.save(user)) {
            return ResponseEntity.ok(user);
        } else {
            throw new InternalServerErrorException("注册失败");
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/favorite")
    @Operation(summary = "收藏", description = "type类型可选：team, player, match, event")
    public void favorite(Long userId, String type, Long id) {
        if (userId == null || type == null || id == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        switch (type) {
            case "team":
                userService.favoriteTeam(userId, id);
                break;
            case "player":
                userService.favoritePlayer(userId, id);
                break;
            case "match":
                userService.favoriteMatch(userId, id);
                break;
            case "event":
                userService.favoriteEvent(userId, id);
                break;
            default:
                throw new BadRequestException("参数错误");
        }
    }

    @PostMapping("/unfavorite")
    @Operation(summary = "取消收藏", description = "type类型可选：team, player, match, event")
    public void unfavorite(Long userId, String type, Long id) {
        if (userId == null || type == null || id == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        switch (type) {
            case "team":
                userService.unfavoriteTeam(userId, id);
                break;
            case "player":
                userService.unfavoritePlayer(userId, id);
                break;
            case "match":
                userService.unfavoriteMatch(userId, id);
                break;
            case "event":
                userService.unfavoriteEvent(userId, id);
                break;
            default:
                throw new BadRequestException("参数错误");
        }
    }

    @GetMapping("/favorite")
    @Operation(summary = "获取收藏", description = "type类型可选：team, player, match, event")
    public List<?> getFavorite(Long userId, String type) {
        if (userId == null || type == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        return switch (type) {
            case "team" -> userService.getFavoriteTeams(userId);
            case "player" -> userService.getFavoritePlayers(userId);
            case "match" -> userService.getFavoriteMatches(userId);
            case "event" -> userService.getFavoriteEvents(userId);
            default -> throw new BadRequestException("参数错误");
        };
    }
}
