package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sustech.football.entity.User;
import com.sustech.football.exception.*;
import com.sustech.football.service.UserService;
import com.sustech.football.utils.WXBizDataCrypt;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONObject;
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
    private String appid = "wxca12f9a07b0c63e2";
    private String appsecret = "1d3743bc2b7b109493ba284ccbaa2420";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/wxLogin")
    public ResponseEntity<String> wxLogin(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(String openid, String session_key) {
        if (openid == null || session_key == null) {
            throw new BadRequestException("openid和session_key不能为空");
        }
        System.out.println(openid + "\n" + session_key);
//        String access_token = "ACCESS_TOKEN";
        // signature = hmac_sha256(session_key, "")
//        String url = "GET https://api.weixin.qq.com/wxa/checksession?access_token=ACCESS_TOKEN&signature=SIGNATURE&openid=OPENID&sig_method=hmac_sha256";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("openid", openid);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            user = new User(openid, session_key);
            if (!userService.save(user)) {
                throw new InternalServerErrorException("注册失败");
            }
        } else {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("openid", openid).set("session_key", session_key);
            if (!userService.update(updateWrapper)) {
                throw new InternalServerErrorException("???");
            }
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/get")
    public User getUser(Long userId) {
        if (userId == null) {
            throw new BadRequestException("参数错误");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        return user;
    }

    @PostMapping("/update")
    public void update(Long userId, @RequestParam(required = false) String avatarUrl, @RequestParam(required = false) String nickName) {
        if (userId == null || avatarUrl == null || nickName == null) {
            throw new BadRequestException("参数错误");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            user.setAvatarUrl(avatarUrl);
        }
        if (nickName != null && !nickName.isEmpty()) {
            user.setNickName(nickName);
        }
        if (!userService.updateById(user)) {
            throw new InternalServerErrorException("更新失败");
        }
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
            case "team"->userService.favoriteTeam(userId, id);
            case "user"->userService.favoriteUser(userId, id);
            case "match"->userService.favoriteMatch(userId, id);
            case "event"->userService.favoriteEvent(userId, id);
            default->throw new BadRequestException("参数错误");
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
            case "team" -> userService.unfavoriteTeam(userId, id);
            case "user" -> userService.unfavoriteUser(userId, id);
            case "match" -> userService.unfavoriteMatch(userId, id);
            case "event" -> userService.unfavoriteEvent(userId, id);
            default -> throw new BadRequestException("参数错误");
        }
    }

    @GetMapping("/favorite")
    @Operation(summary = "获取收藏", description = "type类型可选：team, user, match, event")
    public List<?> getFavorite(Long userId, String type) {
        if (userId == null || type == null) {
            throw new BadRequestException("参数错误");
        }
        if (userService.getById(userId) == null) {
            throw new BadRequestException("用户不存在");
        }
        return switch (type) {
            case "team" -> userService.getFavoriteTeams(userId);
            case "user" -> userService.getFavoriteUsers(userId);
            case "match" -> userService.getFavoriteMatches(userId);
            case "event" -> userService.getFavoriteEvents(userId);
            default -> throw new BadRequestException("参数错误");
        };
    }

    @GetMapping("/getData")
    public String getData(String encryptedData, String iv, String userId) {
        User user = userService.getById(Long.parseLong(userId));
        if (user == null) {
            throw new UnauthorizedAccessException("用户不存在");
        }
        String sessionKey = user.getSessionKey();
        WXBizDataCrypt crypt = new WXBizDataCrypt(appid, sessionKey);
        System.out.println(encryptedData);
        System.out.println(iv);
        System.out.println(appid);
        System.out.println(sessionKey);
        String result = "";
        try {
            JSONObject decryptedData = crypt.decryptData(encryptedData, iv);
            result = decryptedData.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("解密失败");
        }
        return result;
    }
}
