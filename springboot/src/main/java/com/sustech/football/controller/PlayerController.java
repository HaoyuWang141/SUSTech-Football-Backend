package com.sustech.football.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.log.Log;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.service.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/player")
@Tag(name = "Player Controller", description = "球员管理接口")
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
    @Operation(summary = "创建球员", description = "创建一个新的球员")
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
    @Operation(summary = "获取球员", description = "提供球员 ID，根据 ID 获取球员信息")
    public Player getPlayerById(Long id) {
        Player player = playerService.getById(id);
        if (player == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        return player;
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有球员", description = "获取所有球员信息")
    public List<Player> getAllPlayers() {
        return playerService.list();
    }

    @GetMapping("/getByIdList")
    @Operation(summary = "获取球员列表", description = "提供球员 ID 列表，获取球员信息")
    public List<Player> getPlayersByIds(@RequestParam List<Long> idList) {
        return playerService.listByIds(idList);
    }

    @PutMapping("/update")
    @Operation(summary = "更新球员", description = "更新球员信息")
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

//    @Deprecated
//    @DeleteMapping("/delete")
//    public void deletePlayer(Long id) {
//        if (!playerService.removeById(id)) {
//            throw new BadRequestException("删除球员失败");
//        }
//    }
//
//    @Deprecated
//    @PostMapping("/joinTeam")
//    public void joinTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
//        if (playerId == null || teamId == null) {
//            throw new BadRequestException("球员id和球队id不能为空");
//        }
//        if (playerService.getById(playerId) == null) {
//            throw new ResourceNotFoundException("球员不存在");
//        }
//        if (teamService.getById(teamId) == null) {
//            throw new ResourceNotFoundException("球队不存在");
//        }
//        TeamPlayer teamPlayer = new TeamPlayer(playerId, teamId);
//        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
//            throw new ConflictException("球员已经加入该球队");
//        }
//        if (!teamPlayerService.save(teamPlayer)) {
//            throw new RuntimeException("加入球队失败");
//        }
//    }

    @PostMapping("/team/applyToJoin")
    @Operation(summary = "申请加入球队", description = "提供球员 ID 和球队 ID，申请加入球队")
    @Parameters({
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "teamId", description = "球队 ID", required = true)
    })
    public void applyJoinTeam(Long playerId, Long teamId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("传参含空值");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        TeamPlayer teamPlayer = new TeamPlayer(playerId, teamId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("已加入球队");
        }

        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest();
        teamPlayerRequest.setTeamId(teamId);
        teamPlayerRequest.setPlayerId(playerId);
        teamPlayerRequest.setType(TeamPlayerRequest.TYPE_APPLICATION);
        teamPlayerRequest.setStatus(TeamPlayerRequest.STATUS_PENDING);
        if (teamPlayerRequestService.selectByMultiId(teamPlayerRequest) != null) {
            throw new ConflictException("曾经已申请");
        }
        if (!teamPlayerRequestService.save(teamPlayerRequest)) {
            throw new RuntimeException("申请失败");
        }
    }

    @GetMapping("/team/getApplications")
    @Operation(summary = "获取球员申请", description = "提供球员 ID，获取球员的所有申请")
    @Parameters({
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "status", description = "申请状态")
    })
    public List<TeamPlayerRequest> getTeamApplications(Long playerId, @RequestParam(required = false) String status) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        List<TeamPlayerRequest> teamPlayerRequests = teamPlayerRequestService.listWithTeam(playerId, TeamPlayerRequest.TYPE_APPLICATION);
        if (status != null) {
            teamPlayerRequests.removeIf(request -> !request.getStatus().equals(status));
        }
        return teamPlayerRequests;
    }

    @PostMapping("/team/readApplications")
    @Operation(summary = "表示已经阅读申请回复", description = "提供球员 ID，表示已经阅读该球员所有申请的回复")
    @Parameter(name = "playerId", description = "球员 ID", required = true)
    public void readApplications(Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        List<TeamPlayerRequest> teamPlayerRequests = teamPlayerRequestService.list(
                new QueryWrapper<TeamPlayerRequest>()
                        .eq("player_id", playerId)
                        .eq("type", TeamPlayerRequest.TYPE_APPLICATION)
                        .eq("has_read", false)
        );
        teamPlayerRequests.forEach(request -> {
            request.setHasRead(true);
            teamPlayerRequestService.updateByMultiId(request);
        });
    }

    @GetMapping("/team/getInvitations")
    @Operation(summary = "获取球员邀请", description = "提供球员 ID，获取球员的所有邀请")
    @Parameters({
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "status", description = "邀请状态")
    })
    public List<TeamPlayerRequest> getTeamInvitations(Long playerId, @RequestParam(required = false) String status) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        List<TeamPlayerRequest> teamPlayerRequests = teamPlayerRequestService.listWithTeam(playerId, TeamPlayerRequest.TYPE_INVITATION);
        if (status != null) {
            teamPlayerRequests.removeIf(request -> !request.getStatus().equals(status));
        }
        return teamPlayerRequests;
    }

    @PostMapping("/team/replyInvitation")
    @Operation(summary = "回复球队邀请", description = "提供球员 ID，球队 ID 和回复状态，回复球队邀请")
    @Parameters({
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "accept", description = "是否接受邀请", required = true)
    })
    public void replyTeamInvitation(Long playerId, Long teamId, Boolean accept) {
        if (playerId == null || teamId == null || accept == null) {
            throw new BadRequestException("球员id、球队id和状态不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!playerService.replyTeamInvitation(playerId, teamId, accept)) {
            throw new BadRequestException("处理邀请失败");
        }
    }

    @GetMapping("/team/getAll")
    @Operation(summary = "获取球员所在球队", description = "提供球员 ID，获取球员所在的所有球队")
    @Parameter(name = "playerId", description = "球员 ID", required = true)
    public List<Team> getTeams(Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        List<TeamPlayer> teamPlayers = teamPlayerService.list(new QueryWrapper<TeamPlayer>().eq("player_id", playerId));
        if (teamPlayers.isEmpty()) {
            return List.of();
        }
        return teamService.listByIds(teamPlayers.stream().map(TeamPlayer::getTeamId).toList());
    }

    @PostMapping("/team/exit")
    public void exitTeam(Long playerId, Long teamId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("传参含空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!playerService.exitTeam(playerId, teamId)) {
            throw new InternalServerErrorException("退出失败");
        }
    }

    @GetMapping("/match/getAll")
    @Operation(summary = "获取球员比赛", description = "提供球员 ID，获取球员参加的所有比赛")
    @Parameter(name = "playerId", description = "球员 ID", required = true)
    public List<Match> getMatches(Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        return playerService.getPlayerMatches(playerId);
    }

    @GetMapping("/event/getAll")
    @Operation(summary = "获取球员事件", description = "提供球员 ID，获取球员参加的所有事件")
    @Parameter(name = "playerId", description = "球员 ID", required = true)
    public List<Event> getEvents(Long playerId) {
        if (playerId == null) {
            throw new BadRequestException("球员id不能为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        List<TeamPlayer> teamPlayers = teamPlayerService.list(new QueryWrapper<TeamPlayer>().eq("player_id", playerId));
        return teamPlayers.stream()
                .map(TeamPlayer::getTeamId)
                .map(teamService::getEvents)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

}
