package com.sustech.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.springboot.entity.User;
import com.sustech.springboot.exception.BadRequestException;
import com.sustech.springboot.exception.ConflictException;
import com.sustech.springboot.exception.UnauthorizedAccessException;
import com.sustech.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
            throw new BadRequestException("注册失败");
        }
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }
}
