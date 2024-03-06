package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/wxLogin")
    public ResponseEntity<String> wxLogin(String code) {
        String appid = "wxca12f9a07b0c63e2";
        String appsecret = "1d3743bc2b7b109493ba284ccbaa2420";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(String openid, String session_key) {
        if (openid == null || session_key == null) {
            throw new BadRequestException("openid和session_key不能为空");
        }
//        String access_token = "ACCESS_TOKEN";
        // signature = hmac_sha256(session_key, "")
//        String url = "GET https://api.weixin.qq.com/wxa/checksession?access_token=ACCESS_TOKEN&signature=SIGNATURE&openid=OPENID&sig_method=hmac_sha256";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", openid);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            user = new User(null, openid, session_key);
            if (!userService.save(user)) {
                throw new InternalServerErrorException("注册失败");
            }
        } else {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username", openid).set("password", session_key);
            if (!userService.update(updateWrapper)) {
                throw new InternalServerErrorException("???");
            }
        }

        return ResponseEntity.ok().body(user.getUserId());
    }

    @PostMapping("/register")
    @Deprecated
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
