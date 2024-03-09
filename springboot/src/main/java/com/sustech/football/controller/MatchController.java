package com.sustech.football.controller;

import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.service.MatchService;

import com.sustech.football.service.PlayerService;
import com.sustech.football.service.TeamService;
import com.sustech.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    @Transactional
    public Match createMatch(Long ownerId, @RequestBody Match match) {
        if (ownerId == null) {
            throw new BadRequestException("比赛所有者ID不能为空");
        }
        if (userService.getById(ownerId) == null) {
            throw new BadRequestException("比赛所有者不存在");
        }
        if (match == null) {
            throw new BadRequestException("比赛信息不能为空");
        }
        if (match.getMatchId() != null) {
            throw new BadRequestException("新建的比赛, 其ID不能存在");
        }
        if (!matchService.save(match)) {
            throw new BadRequestException("创建比赛失败");
        }
        if (!matchService.inviteManager(new MatchManager(match.getMatchId(), ownerId, true))) {
            throw new BadRequestException("创建比赛失败");
        }
        return match;
    }

    @GetMapping("/get")
    public Match getMatch(Long id) {
        if (id == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        Match match = matchService.getMatch(id);
        if (match == null) {
            throw new BadRequestException("比赛不存在");
        }
        return match;
    }

    @GetMapping("/getAll")
    public List<Match> getAllMatches() {
        return matchService.list();
    }

    @PutMapping("/update")
    public void updateMatch(@RequestBody Match match) {
        if (match == null) {
            throw new BadRequestException("比赛信息不能为空");
        }
        if (match.getMatchId() == null) {
            throw new BadRequestException("更新的比赛, 其ID不能为空");
        }
        if (!matchService.updateById(match)) {
            throw new BadRequestException("更新比赛失败");
        }
    }

    @DeleteMapping("/delete")
    public void deleteMatch(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (!matchService.removeById(matchId)) {
            throw new BadRequestException("删除比赛失败");
        }
    }

    @PostMapping("/manager/invite")
    public void inviteManager(Long matchId, Long managerId) {
        if (matchId == null || managerId == null) {
            throw new BadRequestException("比赛ID和管理员ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        if (matchService.inviteManager(new MatchManager(matchId, managerId, false))) {
            throw new RuntimeException("邀请管理员失败");
        }
    }

    @GetMapping("/manager/getAll")
    public List<Long> getManagers(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        return matchService.getManagers(matchId);
    }

    @DeleteMapping("/manager/delete")
    public void deleteManager(Long matchId, Long managerId) {
        if (matchId == null || managerId == null) {
            throw new BadRequestException("比赛ID和管理员ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(managerId) == null) {
            throw new BadRequestException("管理员不存在");
        }
        if (!matchService.deleteManager(new MatchManager(matchId, managerId, false))) {
            throw new BadRequestException("删除管理员失败");
        }
    }

    @PostMapping("/team/invite")
    public void inviteTeam(Long matchId, Long teamId, Boolean isHomeTeam) {
        if (matchId == null || teamId == null || isHomeTeam == null) {
            throw new BadRequestException("传入的参数不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (teamService.getById(teamId) == null) {
            throw new BadRequestException("球队不存在");
        }
        String type = isHomeTeam ? MatchTeamRequest.TYPE_HOME : MatchTeamRequest.TYPE_AWAY;
        if (!matchService.inviteTeam(new MatchTeamRequest(matchId, teamId, type))) {
            throw new BadRequestException("邀请球队失败");
        }

    }

    @DeleteMapping("/team/delete")
    public void deleteTeam(Long matchId, Boolean isHomeTeam) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        String type = isHomeTeam ? MatchTeamRequest.TYPE_HOME : MatchTeamRequest.TYPE_AWAY;
        if (!matchService.deleteTeam(matchId, isHomeTeam)) {
            throw new BadRequestException("删除球队失败");
        }
    }

    @PostMapping("/referee/invite")
    public void inviteReferee(Long matchId, Long refereeId) {
        if (matchId == null || refereeId == null) {
            throw new BadRequestException("比赛ID和裁判ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (!matchService.inviteReferee(new MatchReferee(matchId, refereeId))) {
            throw new BadRequestException("邀请裁判失败");
        }

    }

    @GetMapping("/referee/getAll")
    public List<Referee> getReferees(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        return matchService.getReferees(matchId);
    }

    @DeleteMapping("/referee/delete")
    public void deleteReferee(Long matchId, Long refereeId) {
        if (matchId == null || refereeId == null) {
            throw new BadRequestException("比赛ID和裁判ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (!matchService.deleteReferee(new MatchReferee(matchId, refereeId))) {
            throw new BadRequestException("删除裁判失败");
        }
    }

    @PostMapping("/referee/updateResult")
    public void updateResult(Long refereeId, Match match) {
        if (refereeId == null || match == null) {
            throw new BadRequestException("裁判ID和比赛信息不能为空");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (match.getMatchId() == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(match.getMatchId()) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (!matchService.updateResult(refereeId, match)) {
            throw new BadRequestException("更新比赛结果失败");
        }
    }

    @PostMapping("/referee/addPlayerAction")
    public void updatePlayerAction(Long refereeId, @RequestBody MatchPlayerAction matchPlayerAction) {
        if (refereeId == null || matchPlayerAction == null) {
            throw new BadRequestException("裁判ID和比赛球员动作信息不能为空");
        }
        if (userService.getById(refereeId) == null) {
            throw new BadRequestException("裁判不存在");
        }
        if (matchPlayerAction.getMatchId() == null) {
            throw new BadRequestException("比赛ID不能为空");
        }
        if (matchService.getById(matchPlayerAction.getMatchId()) == null) {
            throw new BadRequestException("比赛不存在");
        }
        if (matchPlayerAction.getTeamId() == null) {
            throw new BadRequestException("球队ID不能为空");
        }
        if (teamService.getById(matchPlayerAction.getTeamId()) == null) {
            throw new BadRequestException("球队不存在");
        }
        if (matchPlayerAction.getPlayerId() == null) {
            throw new BadRequestException("球员ID不能为空");
        }
        if (playerService.getById(matchPlayerAction.getPlayerId()) == null) {
            throw new BadRequestException("球员不存在");
        }
        if (!matchService.addPlayerAction(refereeId, matchPlayerAction)) {
            throw new BadRequestException("更新比赛球员动作失败");
        }
    }

    @GetMapping("/getEvent")
    public Event getEvent(Long matchId, Long eventId) {
        if (matchId == null || eventId == null) {
            throw new BadRequestException("比赛ID和事件ID不能为空");
        }
        if (matchService.getById(matchId) == null) {
            throw new BadRequestException("比赛不存在");
        }
        return null;
        // TODO: 2024-3-2
//        return matchService.getEvent(matchId, eventId);
    }
}
