package com.sustech.springboot.controller;

import com.sustech.springboot.entity.Player;
import com.sustech.springboot.exception.BadRequestException;
import com.sustech.springboot.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getById(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (playerService.save(player)) {
            return ResponseEntity.ok(player);
        } else {
            throw new BadRequestException("创建球员失败");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        if (playerService.updateById(player)) {
            return ResponseEntity.ok(player);
        } else {
            throw new BadRequestException("更新球员失败");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.removeById(id)) {
            return ResponseEntity.ok().build();
        } else {
            throw new BadRequestException("删除球员失败");
        }
    }
}

