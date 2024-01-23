package com.sustech.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.springboot.entity.Player;
import com.sustech.springboot.entity.TeamPlayer;
import com.sustech.springboot.entity.TeamPlayerRequest;
import com.sustech.springboot.exception.BadRequestException;
import com.sustech.springboot.exception.ConflictException;
import com.sustech.springboot.exception.ResourceNotFoundException;
import com.sustech.springboot.service.PlayerService;
import com.sustech.springboot.service.TeamPlayerRequestService;
import com.sustech.springboot.service.TeamPlayerService;
import com.sustech.springboot.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final TeamPlayerService teamPlayerService;
    private final TeamPlayerRequestService teamPlayerRequestService;

    @Autowired
    public PlayerController(PlayerService playerService,
                            TeamService teamService,
                            TeamPlayerService teamPlayerService,
                            TeamPlayerRequestService teamPlayerRequestService) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.teamPlayerService = teamPlayerService;
        this.teamPlayerRequestService = teamPlayerRequestService;
    }

    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (playerService.save(player)) {
            return ResponseEntity.ok(player);
        } else {
            throw new BadRequestException("创建球员失败");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.list());
    }

    @PutMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        if (playerService.updateById(player)) {
            return ResponseEntity.ok(player);
        } else {
            throw new BadRequestException("更新球员失败");
        }
    }

    @Deprecated
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.removeById(id)) {
            return ResponseEntity.ok().build();
        } else {
            throw new BadRequestException("删除球员失败");
        }
    }

    @Deprecated
    @PostMapping("/joinTeam")
    public ResponseEntity<Void> joinTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("球员id和球队id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        TeamPlayer teamPlayer = new TeamPlayer(playerId, teamId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("球员已经加入该球队");
        }
        if (!teamPlayerService.save(teamPlayer)) {
            throw new RuntimeException("加入球队失败");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requestJoinTeam")
    public ResponseEntity<Void> requestJoinTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("球员id和球队id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        TeamPlayer teamPlayer = new TeamPlayer(playerId, teamId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("球员已经加入球队");
        }

        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest(playerId, teamId,
                TeamPlayerRequest.TYPE_APPLICATION,
                TeamPlayerRequest.STATUS_PENDING);
        if (teamPlayerRequestService.selectByMultiId(teamPlayerRequest) != null) {
            throw new ConflictException("球员已经申请加入球队");
        }
        if (!teamPlayerRequestService.save(teamPlayerRequest)) {
            throw new RuntimeException("申请加入球队失败");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getTeamInvitations")
    public ResponseEntity<List<TeamPlayerRequest>> getTeamInvitations(@RequestParam Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        QueryWrapper<TeamPlayerRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId);
        queryWrapper.like("type", TeamPlayerRequest.TYPE_INVITATION);
        return ResponseEntity.ok(teamPlayerRequestService.list(queryWrapper));
    }

    @PostMapping("/responseTeamInvitation")
    public ResponseEntity<Void> responseTeamInvitation(@RequestParam Long playerId, @RequestParam Long teamId, @RequestParam Boolean accept) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("球员id和球队id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        TeamPlayer teamPlayer = new TeamPlayer(playerId, teamId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("球员已经加入球队");
        }

        String status = accept ? TeamPlayerRequest.STATUS_ACCEPTED : TeamPlayerRequest.STATUS_REJECTED;
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest(playerId, teamId,
                TeamPlayerRequest.TYPE_INVITATION, status);
        if (teamPlayerRequestService.selectByMultiId(teamPlayerRequest) == null) {
            throw new ConflictException("球员未收到邀请");
        }
        if (!teamPlayerRequestService.updateById(teamPlayerRequest)) {
            throw new RuntimeException("回应邀请失败");
        }
        return ResponseEntity.ok().build();
    }
}

