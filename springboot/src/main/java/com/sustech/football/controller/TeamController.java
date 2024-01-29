package com.sustech.football.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sustech.football.service.*;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;

import java.util.List;

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

    @PostMapping("/create")
    public Team createTeam(@RequestBody Team team) {
        if (team == null) {
            throw new BadRequestException("传入球队为空");
        }
        if (team.getTeamId() != null) {
            throw new BadRequestException("传入球队的ID不为空");
        }
        if (!teamService.save(team)) {
            throw new BadRequestException("创建球队失败");
        }
        return team;
    }

    @GetMapping("/get")
    public Team getTeamById(Long id) {
        Team team = teamService.getById(id);
        if (team == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return team;
    }

    @GetMapping("/getAll")
    public List<Team> getAllTeams() {
        return teamService.list();
    }

    @PutMapping("/update")
    public Team updateTeam(@RequestBody Team team) {
        if (team == null) {
            throw new BadRequestException("传入球队为空");
        }
        if (team.getTeamId() == null) {
            throw new BadRequestException("传入球队的ID为空");
        }
        if (!teamService.updateById(team)) {
            throw new BadRequestException("更新球队失败");
        }
        return team;
    }

    @Deprecated
    @DeleteMapping("/delete")
    public void deleteTeam(Long id) {
        if (!teamService.removeById(id)) {
            throw new ResourceNotFoundException("球队不存在");
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
        teamService.inviteManager(new TeamManager(managerId, teamId));
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
        teamService.deleteManager(new TeamManager(teamId, managerId));
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
        teamService.invitePlayer(new TeamPlayer(teamId, playerId));
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
        teamService.replyPlayerApplication(teamId, playerId, accept);
    }

    @GetMapping("/player/getAll")
    public List<Player> getPlayers(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("传入的队伍ID为空");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        return teamService.getPlayers(teamId);
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
        teamService.deletePlayer(new TeamPlayer(teamId, playerId));
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
        teamService.inviteCoach(new TeamCoach(teamId, coachId));
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
        teamService.deleteCoach(new TeamCoach(teamId, coachId));
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
        teamService.replyMatchInvitation(teamId, matchId, accept);
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
        teamService.requestJoinEvent(new EventTeam(eventId, teamId));
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
        if (matchService.getById(eventId) == null) {
            throw new ResourceNotFoundException("赛事不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new ResourceNotFoundException("球队不存在");
        }
        teamService.replyEventInvitation(teamId, eventId, accept);
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
        teamService.addUniform(new TeamUniform(teamId, uniformUrl));
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
        teamService.deleteUniform(new TeamUniform(teamId, uniformUrl));
    }

}
