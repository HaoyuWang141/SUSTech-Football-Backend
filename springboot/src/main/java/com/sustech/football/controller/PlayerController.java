package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Player createPlayer(@RequestBody Player player) {
        if (player == null) {
            throw new BadRequestException("传入球员为空");
        }
        if (player.getPlayerId() != null) {
            throw new BadRequestException("传入球员的ID不为空");
        }
        if (!playerService.save(player)) {
            throw new BadRequestException("创建球员失败");
        }
        return player;
    }

    @GetMapping("/get")
    public Player getPlayerById(Long id) {
        Player player = playerService.getById(id);
        if (player == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        return player;
    }

    @GetMapping("/getAll")
    public List<Player> getAllPlayers() {
        return playerService.list();
    }

    @PutMapping("/update")
    public Player updatePlayer(@RequestBody Player player) {
        if (player == null) {
            throw new BadRequestException("传入球员为空");
        }
        if (player.getPlayerId() == null) {
            throw new BadRequestException("传入球员的ID为空");
        }
        if (!playerService.updateById(player)) {
            throw new BadRequestException("更新球员失败");
        }
        return player;
    }

    @Deprecated
    @DeleteMapping("/delete")
    public void deletePlayer(Long id) {
        if (!playerService.removeById(id)) {
            throw new BadRequestException("删除球员失败");
        }
    }

    @Deprecated
    @PostMapping("/joinTeam")
    public void joinTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
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
    }

    @PostMapping("/team/applyToJoin")
    public void applyJoinTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
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
    }

    @GetMapping("/team/getInvitations")
    public List<TeamPlayerRequest> getTeamInvitations(@RequestParam Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        return teamPlayerRequestService.listWithTeam(playerId, TeamPlayerRequest.TYPE_INVITATION);
    }

    @PostMapping("/team/replyInvitation")
    public void replyTeamInvitation(
            @RequestParam Long playerId,
            @RequestParam Long teamId,
            @RequestParam Boolean accept) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("球员id和球队id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        TeamPlayer teamPlayer = new TeamPlayer(teamId, playerId);
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
        if (accept) {
            if (!teamPlayerService.save(teamPlayer)) {
                throw new RuntimeException("加入球队失败");
            }
        }
    }

    @GetMapping("/getMatches")
    public List<Match> getMatches(@RequestParam Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        return null;
        // TODO: 2024-3-2
//        return playerService.getMatches(playerId);
    }

}
