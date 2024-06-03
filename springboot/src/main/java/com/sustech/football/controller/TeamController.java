package com.sustech.football.controller;

import com.sustech.football.model.team.VoTeam;

import io.swagger.v3.oas.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.sustech.football.service.*;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoachService coachService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TeamPlayerService teamPlayerService;

    @PostMapping("/create")
    @Operation(summary = "创建球队", description = "创建一个新的球队")
    @Parameter(name = "ownerId", description = "管理员 ID", required = true)
    @Transactional
    public String createTeam(Long ownerId, @RequestBody Team team) {
        if (ownerId == null) {
            throw new BadRequestException("传入的队伍管理员ID为空");
        }
        if (userService.getById(ownerId) == null) {
            throw new ResourceNotFoundException("管理员所示用户不存在");
        }
        if (team == null) {
            throw new BadRequestException("传入球队为空");
        }
        if (team.getTeamId() != null) {
            throw new BadRequestException("传入球队的ID不为空");
        }
        if (!teamService.save(team)) {
            throw new BadRequestException("创建球队失败");
        }
        if (!teamService.inviteManager(new TeamManager(ownerId, team.getTeamId(), true))) {
            throw new BadRequestException("创建球队失败");
        }
        return "创建球队成功";
    }

    @GetMapping("/get")
    @Operation(summary = "获取球队", description = "提供球队 ID，根据 ID 获取球队信息")
    @Parameter(name = "id", description = "球队 ID", required = true)
    public VoTeam getTeamById(Long id) {
        Team team = teamService.getTeamById(id);
        if (team == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return new VoTeam(
                team.getTeamId(),
                team.getName(),
                team.getLogoUrl(),
                team.getDescription(),
                team.getCaptainId(),
                team.getCoachList(),
                team.getTeamPlayerList().stream().map(teamPlayer -> new VoTeam.VoPlayer(
                        teamPlayer.getPlayerId(),
                        teamPlayer.getPlayer().getName(),
                        teamPlayer.getPlayer().getPhotoUrl(),
                        teamPlayer.getNumber(),
                        teamPlayer.getAppearances(),
                        teamPlayer.getGoals(),
                        teamPlayer.getAssists(),
                        teamPlayer.getYellowCards(),
                        teamPlayer.getRedCards()
                )).collect(Collectors.toList()),
                team.getEventList(),
                team.getMatchList(),
                team.getManagerList().stream().map(user -> {
                    VoTeam.VoUser voUser = new VoTeam.VoUser();
                    voUser.setUserId(user.getUserId());
                    voUser.setAvatarUrl(user.getAvatarUrl());
                    voUser.setNickName(user.getNickName());
                    return voUser;
                }).toList()
        );
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取所有球队", description = "获取所有球队信息")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/getByIdList")
    @Operation(summary = "获取球队列表", description = "根据 ID 列表获取球队信息")
    @Parameter(name = "idList", description = "球队 ID 列表", required = true)
    public List<Team> getTeamsByIdList(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new BadRequestException("传入的队伍ID列表为空");
        }
        return teamService.getTeamsByIdList(idList);
    }

    private record TeamRecord(Long teamId, String name, String logoUrl, Long captainId, String description) {
    }

    @PostMapping("captain/updateByPlayerId")
    @Operation(summary = "更新队长", description = "根据队伍 ID 和队长 ID 更新队长")
    @Parameters({
            @Parameter(name = "teamId", description = "队伍 ID", required = true),
            @Parameter(name = "captainId", description = "队长 ID", required = true)
    })
    public void updateCaptain(Long teamId, Long captainId) {
        if (teamId == null || captainId == null) {
            throw new BadRequestException("传入的队伍ID或队长ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (playerService.getById(captainId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (!teamService.updateCaptainByPlayerId(teamId, captainId)) {
            throw new BadRequestException("更新队长失败");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新球队", description = "更新球队信息")
    public void updateTeam(Long managerId, @RequestBody TeamRecord teamRecord) {
        if (managerId == null || teamRecord == null) {
            throw new BadRequestException("传参含空值");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员非法");
        }
        Long teamId = teamRecord.teamId();
        if (teamId == null) {
            throw new BadRequestException("球队id为空");
        }
        Team team = teamService.getById(teamId);
        if (team == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        List<Long> managerIdList = teamService.getManagers(teamId);
        if (managerIdList == null || !managerIdList.contains(managerId)) {
            throw new BadRequestException("无权修改");
        }
        if (teamRecord.name() != null) {
            team.setName(teamRecord.name());
        }
        if (teamRecord.logoUrl() != null) {
            team.setLogoUrl(teamRecord.logoUrl());
        }
        if (teamRecord.description() != null) {
            team.setDescription(teamRecord.description());
        }
//        if (teamRecord.captainId() != null) {
//            List<Long> playerIdList = teamPlayerService.list(new QueryWrapper<TeamPlayer>().eq("team_id", teamId))
//                    .stream()
//                    .map(TeamPlayer::getPlayerId)
//                    .toList();
//            if (playerIdList == null || !playerIdList.contains(teamRecord.captainId())) {
//                throw new BadRequestException("队长非队员");
//            }
//            team.setCaptainId(teamRecord.captainId());
//        }
        if (!teamService.updateById(team)) {
            throw new BadRequestException("更新失败");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除球队", description = "删除球队")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "userId", description = "用户 ID", required = true)
    })
    public void deleteTeam(Long teamId, Long userId) {
//        if (!teamService.removeById(id)) {
//            throw new ResourceNotFoundException("球队不存在");
//        }
        if (teamId == null || userId == null) {
            throw new BadRequestException("传入的管理员ID或队伍ID为空");
        }
        if (userService.getById(userId) == null) {
            throw new ResourceNotFoundException("删除的操作用户不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.deleteTeam(teamId, userId)) {
            throw new ConflictException("删除失败，队伍已有关联");
        }

    }

    @PostMapping("/manager/invite")
    @Operation(summary = "邀请管理员", description = "提供管理员 ID 和球队 ID，邀请管理员")
    @Parameters({
            @Parameter(name = "managerId", description = "管理员 ID", required = true),
            @Parameter(name = "teamId", description = "球队 ID", required = true)
    })
    public void inviteManager(@RequestParam Long managerId, @RequestParam Long teamId) {
        if (managerId == null || teamId == null) {
            throw new BadRequestException("传入的管理员ID或队伍ID为空");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.inviteManager(new TeamManager(managerId, teamId, false))) {
            throw new BadRequestException("邀请管理员失败");
        }
    }

    @GetMapping("/manager/getAll")
    @Operation(summary = "获取所有管理员", description = "获取所有管理员信息")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<Long> getManagers(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getManagers(teamId);
    }

    @DeleteMapping("/manager/delete")
    @Operation(summary = "删除管理员", description = "提供管理员 ID 和球队 ID，删除管理员")
    @Parameters({
            @Parameter(name = "managerId", description = "管理员 ID", required = true),
            @Parameter(name = "teamId", description = "球队 ID", required = true)
    })
    public void deleteManager(Long teamId, Long managerId) {
        if (managerId == null || teamId == null) {
            throw new BadRequestException("传入的管理员ID或队伍ID为空");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.deleteManager(new TeamManager(teamId, managerId, false))) {
            throw new BadRequestException("删除管理员失败");
        }
    }

    @PostMapping("/player/invite")
    @Operation(summary = "邀请球员", description = "提供球员 ID 和球队 ID，邀请球员")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true)
    })
    public void invitePlayer(Long teamId, Long playerId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("传入的球员ID或队伍ID为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.invitePlayer(new TeamPlayer(teamId, playerId))) {
            throw new BadRequestException("邀请球员失败");
        }
    }

    @GetMapping("/player/getInvitations")
    @Operation(summary = "获取球员邀请", description = "根据队伍 ID，获取球员的邀请")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<TeamPlayerRequest> getPlayerInvitations(@RequestParam Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getPlayerInvitations(teamId);
    }

    @GetMapping("/player/getApplications")
    @Operation(summary = "获取球员申请", description = "根据队伍 ID，获取球员的申请")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<TeamPlayerRequest> getPlayerApplications(@RequestParam Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getPlayerApplications(teamId);
    }

    @PostMapping("/player/replyApplication")
    @Operation(summary = "回复球员申请", description = "提供球员 ID，队伍 ID 和回复状态，回复球员申请")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "accept", description = "是否接受申请", required = true)
    })
    public void replyPlayerApplication(Long teamId, Long playerId, Boolean accept) {
        if (playerId == null || teamId == null || accept == null) {
            throw new BadRequestException("传入的球员ID或队伍ID或状态为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.replyPlayerApplication(teamId, playerId, accept)) {
            throw new BadRequestException("回复球员申请失败");
        }
    }

    @GetMapping("/player/getAll")
    @Operation(summary = "获取球队球员", description = "根据队伍 ID，获取球队的所有球员")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<VoTeam.VoPlayer> getPlayers(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        List<TeamPlayer> teamPlayerList = teamService.getTeamPlayers(teamId);
        return teamPlayerList.stream().map(teamPlayer -> new VoTeam.VoPlayer(
                teamPlayer.getPlayerId(),
                teamPlayer.getPlayer().getName(),
                teamPlayer.getPlayer().getPhotoUrl(),
                teamPlayer.getNumber(),
                teamPlayer.getAppearances(),
                teamPlayer.getGoals(),
                teamPlayer.getAssists(),
                teamPlayer.getYellowCards(),
                teamPlayer.getRedCards()
        )).toList();
    }

    @PostMapping("/player/updateNumber")
    @Operation(summary = "更新球员号码", description = "提供球员 ID，队伍 ID 和号码，更新球员号码")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "number", description = "号码", required = true)
    })
    public void updatePlayerNumber(Long teamId, Long playerId, Integer number) {
        if (playerId == null || teamId == null || number == null) {
            throw new BadRequestException("传入的球员ID或队伍ID或号码为空");
        }
        if (number < 0 || number > 99) {
            throw new BadRequestException("号码不合法");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamPlayerService.updatePlayerNumber(teamId, playerId, number)) {
            throw new BadRequestException("更新球员号码失败");
        }
    }

    @PostMapping("/player/retire")
    @Operation(summary = "退役球员", description = "提供球员 ID 和队伍 ID，退役球员")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true)
    })
    public void retirePlayer(Long teamId, Long playerId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("传入的球员ID或队伍ID为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.retirePlayer(teamId, playerId)) {
            throw new BadRequestException("删除球员失败");
        }
    }

    @PostMapping("/player/rehire")
    @Operation(summary = "重新雇佣球员", description = "提供球员 ID，队伍 ID 和号码，重新雇佣球员")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true),
            @Parameter(name = "number", description = "号码", required = true)
    })
    public void rehirePlayer(Long teamId, Long playerId, Integer number) {
        if (playerId == null || teamId == null || number == null) {
            throw new BadRequestException("传入的球员ID或队伍ID或号码为空");
        }
        if (number < 0 || number > 99) {
            throw new BadRequestException("号码不合法");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.rehirePlayer(teamId, playerId, number)) {
            throw new BadRequestException("重新雇佣球员失败");
        }
    }

    @DeleteMapping("/player/delete")
    @Operation(summary = "删除球员", description = "提供球员 ID 和队伍 ID，删除球员")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "playerId", description = "球员 ID", required = true)
    })
    public void deletePlayer(Long teamId, Long playerId) {
        if (playerId == null || teamId == null) {
            throw new BadRequestException("传入的球员ID或队伍ID为空");
        }
        if (playerService.getById(playerId) == null) {
            throw new ResourceNotFoundException("球员不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.deletePlayer(teamId, playerId)) {
            throw new BadRequestException("删除球员失败");
        }
    }

    @PostMapping("/coach/invite")
    @Operation(summary = "邀请教练", description = "提供教练 ID 和队伍 ID，邀请教练")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "coachId", description = "教练 ID", required = true)
    })
    public void inviteCoach(Long teamId, Long coachId) {
        if (coachId == null || teamId == null) {
            throw new BadRequestException("传入的教练ID或队伍ID为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.inviteCoach(new TeamCoach(teamId, coachId))) {
            throw new BadRequestException("邀请教练失败");
        }
    }

    @GetMapping("/coach/getAll")
    @Operation(summary = "获取球队教练", description = "根据队伍 ID，获取球队的所有教练")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<Coach> getCoaches(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getCoaches(teamId);
    }

    @DeleteMapping("/coach/delete")
    @Operation(summary = "删除教练", description = "提供教练 ID 和队伍 ID，删除教练")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "coachId", description = "教练 ID", required = true)
    })
    public void deleteCoach(Long teamId, Long coachId) {
        if (coachId == null || teamId == null) {
            throw new BadRequestException("传入的教练ID或队伍ID为空");
        }
        if (coachService.getById(coachId) == null) {
            throw new ResourceNotFoundException("教练不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.deleteCoach(new TeamCoach(teamId, coachId))) {
            throw new BadRequestException("删除教练失败");
        }
    }

    @GetMapping("/match/getInvitations")
    @Operation(summary = "获取比赛邀请", description = "根据队伍 ID，获取球队的比赛邀请")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<MatchTeamRequest> getMatchInvitations(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getMatchInvitations(teamId);
    }

    @PostMapping("/match/replyInvitation")
    @Operation(summary = "回复比赛邀请", description = "提供队伍 ID，比赛 ID 和回复状态，回复比赛邀请")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "matchId", description = "比赛 ID", required = true),
            @Parameter(name = "accept", description = "是否接受邀请", required = true)
    })
    public void replyMatchInvitation(Long teamId, Long matchId, Boolean accept) {
        if (matchId == null || teamId == null || accept == null) {
            throw new BadRequestException("传入的比赛ID或队伍ID或状态为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.replyMatchInvitation(teamId, matchId, accept)) {
            throw new BadRequestException("回复比赛邀请失败");
        }
    }

    @GetMapping("/match/getAll")
    @Operation(summary = "获取球队比赛", description = "根据队伍 ID，获取球队的所有比赛")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<Match> getMatches(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getMatches(teamId);
    }

    @PostMapping("/event/applyToJoin")
    @Operation(summary = "申请加入赛事", description = "提供队伍 ID 和赛事 ID，申请加入赛事")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "eventId", description = "赛事 ID", required = true)
    })
    public void requestJoinEvent(Long teamId, Long eventId) {
        if (eventId == null || teamId == null) {
            throw new BadRequestException("传入的赛事ID或队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (matchService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (!teamService.requestJoinEvent(new EventTeam(eventId, teamId))) {
            throw new BadRequestException("申请加入赛事失败");
        }
    }

    @GetMapping("/event/getInvitations")
    @Operation(summary = "获取赛事邀请", description = "提供队伍 ID，获取赛事的邀请")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<EventTeamRequest> getEventInvitations(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getEventInvitations(teamId);
    }

    @PostMapping("/event/replyInvitation")
    @Operation(summary = "回复赛事邀请", description = "提供队伍 ID，赛事 ID 和回复状态，回复赛事邀请")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "eventId", description = "赛事 ID", required = true),
            @Parameter(name = "accept", description = "是否接受邀请", required = true)
    })
    public void replyEventApplication(Long teamId, Long eventId, Boolean accept) {
        if (eventId == null || teamId == null || accept == null) {
            throw new BadRequestException("传入的赛事ID或队伍ID或状态为空");
        }
        if (eventService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.replyEventInvitation(teamId, eventId, accept)) {
            throw new BadRequestException("回复赛事邀请失败");
        }
    }

    @GetMapping("/event/getAll")
    @Operation(summary = "获取球队赛事", description = "根据队伍 ID，获取球队的所有赛事")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<Event> getEvents(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getEvents(teamId);
    }

    @PostMapping("/uniform/add")
    @Operation(summary = "添加队服", description = "提供队伍 ID 和队服图片 URL，添加队服")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "uniformUrl", description = "队服图片 URL", required = true)
    })
    public void addUniform(Long teamId, String uniformUrl) {
        if (teamId == null || uniformUrl == null) {
            throw new BadRequestException("传入的队伍ID或队服图片URL为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.addUniform(new TeamUniform(teamId, uniformUrl))) {
            throw new BadRequestException("添加队服失败");
        }
    }

    @GetMapping("/uniform/getAll")
    @Operation(summary = "获取队服", description = "根据队伍 ID，获取队伍的所有队服")
    @Parameter(name = "teamId", description = "球队 ID", required = true)
    public List<String> getTeamUniformUrls(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        return teamService.getUniforms(teamId)
                .stream()
                .map(TeamUniform::getUniformUrl)
                .toList();
    }

    @DeleteMapping("/uniform/delete")
    @Operation(summary = "删除队服", description = "提供队伍 ID 和队服图片 URL，删除队服")
    @Parameters({
            @Parameter(name = "teamId", description = "球队 ID", required = true),
            @Parameter(name = "uniformUrl", description = "队服图片 URL", required = true)
    })
    public void deleteUniform(Long teamId, String uniformUrl) {
        if (teamId == null || uniformUrl == null) {
            throw new BadRequestException("传入的队伍ID或队服URL为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.deleteUniform(new TeamUniform(teamId, uniformUrl))) {
            throw new BadRequestException("删除队服失败");
        }
    }

}
