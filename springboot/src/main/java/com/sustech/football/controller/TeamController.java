package com.sustech.football.controller;

import com.sustech.football.model.team.VoTeam;
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
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/getByIdList")
    public List<Team> getTeamsByIdList(@RequestParam List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            throw new BadRequestException("传入的队伍ID列表为空");
        }
        return teamService.getTeamsByIdList(idList);
    }

    private record TeamRecord(Long teamId, String name, String logoUrl, Long captainId, String description) {
    }

    @PostMapping("captain/updateByPlayerId")
    public void updateCaptain(@RequestParam Long teamId, @RequestParam Long captainId) {
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
    public List<Long> getManagers(@RequestParam Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getManagers(teamId);
    }

    @DeleteMapping("/manager/delete")
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
    public void invitePlayer(@RequestParam Long teamId, @RequestParam Long playerId) {
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
    public void replyPlayerApplication(
            @RequestParam Long teamId,
            @RequestParam Long playerId,
            @RequestParam Boolean accept) {
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
    public void addUniform(Long teamId, String uniformUrl) {
        if (teamId == null || uniformUrl == null) {
            throw new BadRequestException("传入的队伍ID或队服URL为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        if (!teamService.addUniform(new TeamUniform(teamId, uniformUrl))) {
            throw new BadRequestException("添加队服失败");
        }
    }

    @GetMapping("/uniform/getAll")
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
