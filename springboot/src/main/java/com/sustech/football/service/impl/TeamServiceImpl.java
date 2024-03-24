
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.*;
import com.sustech.football.mapper.TeamMapper;
import com.sustech.football.service.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
    @Autowired
    private TeamManagerService teamManagerService;
    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired
    private TeamPlayerRequestService teamPlayerRequestService;
    @Autowired
    private TeamCoachService teamCoachService;
    @Autowired
    private TeamCoachRequestService teamCoachRequestService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchTeamRequestService matchTeamRequestService;
    @Autowired
    private EventTeamService eventTeamService;
    @Autowired
    private EventTeamRequestService eventTeamRequestService;
    @Autowired
    private TeamUniformService teamUniformService;
    @Autowired
    private EventMatchService eventMatchService;
    @Autowired
    private UserService userService;

    @Override
    public Team getTeamById(Long teamId) {
        Team team = getById(teamId);
        team.setPlayerList(this.getPlayers(teamId));
        team.setCoachList(this.getCoaches(teamId));
        team.setManagerList(this.getManagers(teamId).stream().map(userService::getById).toList());
        List<Match> matchList = this.getMatches(teamId);
        matchList = matchList.stream()
                .sorted(Comparator.comparing(Match::getTime))
                .peek(match -> {
                    match.setHomeTeam(getById(match.getHomeTeamId()));
                    match.setAwayTeam(getById(match.getAwayTeamId()));
                })
                .toList();
        team.setMatchList(matchList);
        team.setEventList(this.getEvents(teamId));
        return team;
    }

    @Override
    public List<Team> getTeamsByIdList(List<Long> teamIdList) {
        return teamIdList.stream()
                .map(this::getTeamById).toList();
    }

    @Override
    public boolean inviteManager(TeamManager teamManager) {
        if (teamManagerService.selectByMultiId(teamManager) != null) {
            throw new ConflictException("该用户已经是该球队的管理员");
        }
        if (!teamManagerService.saveOrUpdateByMultiId(teamManager)) {
            throw new BadRequestException("邀请管理员失败");
        }
        return true;
    }

    @Override
    public List<Long> getManagers(Long teamId) {
        QueryWrapper<TeamManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        return teamManagerService.list(queryWrapper).stream().map(TeamManager::getUserId).toList();
    }

    @Override
    public boolean deleteManager(TeamManager teamManager) {
        if (!teamManagerService.deleteByMultiId(teamManager)) {
            throw new BadRequestException("删除管理员失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean invitePlayer(TeamPlayer teamPlayer) {
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("该球员已经在球队中");
        }
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest(
                teamPlayer.getTeamId(), teamPlayer.getPlayerId(),
                TeamPlayerRequest.TYPE_INVITATION,
                TeamPlayerRequest.STATUS_PENDING);
        if (teamPlayerRequestService.selectByMultiId(teamPlayerRequest) != null) {
            throw new BadRequestException("曾经向该球员发出过邀请，请勿重复发送");
        }
        if (!teamPlayerRequestService.saveOrUpdateByMultiId(teamPlayerRequest)) {
            throw new BadRequestException("邀请球员失败");
        }
        return true;
    }

    @Override
    public List<TeamPlayerRequest> getPlayerInvitations(Long teamId) {
        return teamPlayerRequestService.listWithPlayer(teamId, TeamPlayerRequest.TYPE_INVITATION);
    }

    @Override
    public List<TeamPlayerRequest> getPlayerApplications(Long teamId) {
        return teamPlayerRequestService.listWithPlayer(teamId, TeamPlayerRequest.TYPE_APPLICATION);
    }

    @Override
    @Transactional
    public boolean replyPlayerApplication(Long teamId, Long playerId, Boolean accept) {
        TeamPlayer teamPlayer = new TeamPlayer(teamId, playerId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ConflictException("该球员已经在球队中");
        }

        String status = accept ? TeamPlayerRequest.STATUS_ACCEPTED : TeamPlayerRequest.STATUS_REJECTED;
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest(teamId, playerId,
                TeamPlayerRequest.TYPE_APPLICATION, status);
        if (teamPlayerRequestService.selectByMultiId(teamPlayerRequest) == null) {
            throw new BadRequestException("该球员没有申请加入球队");
        }
        teamPlayerRequestService.saveOrUpdateRequestWithTime(teamPlayerRequest);
        if (accept) {
            if (!teamPlayerService.saveOrUpdateByMultiId(teamPlayer)) {
                throw new RuntimeException("加入球队失败");
            }
        }
        return true;
    }

    @Override
    public List<Player> getPlayers(Long teamId) {
        return teamPlayerService
                .listWithPlayer(teamId)
                .stream()
                .map(TeamPlayer::getPlayer)
                .toList();
    }

    @Override
    public boolean deletePlayer(TeamPlayer teamPlayer) {
        if (!teamPlayerService.deleteByMultiId(teamPlayer)) {
            throw new BadRequestException("删除球员失败");
        }
        return true;
    }

    @Override
    public boolean inviteCoach(TeamCoach teamCoach) {
        if (teamCoachService.selectByMultiId(teamCoach) != null) {
            throw new ConflictException("该教练已经在球队中");
        }
        TeamCoachRequest teamCoachRequest = new TeamCoachRequest(
                teamCoach.getTeamId(), teamCoach.getCoachId());
        if (teamCoachRequestService.selectByMultiId(teamCoachRequest) != null) {
            throw new ConflictException("曾经向该教练发出过邀请，请勿重复发送");
        }
        if (!teamCoachRequestService.saveOrUpdateByMultiId(teamCoachRequest)) {
            throw new RuntimeException("邀请教练失败");
        }
        return true;
    }

    @Override
    public List<Coach> getCoaches(Long teamId) {
        return teamCoachService
                .listWithCoach(teamId)
                .stream()
                .map(TeamCoach::getCoach)
                .toList();
    }

    @Override
    public boolean deleteCoach(TeamCoach teamCoach) {
        if (!teamCoachService.deleteByMultiId(teamCoach)) {
            throw new BadRequestException("删除教练失败");
        }
        return true;
    }

    @Override
    public List<MatchTeamRequest> getMatchInvitations(Long teamId) {
        return matchTeamRequestService.listWithMatch(teamId);
    }

    @Override
    @Transactional
    public boolean replyMatchInvitation(Long teamId, Long matchId, Boolean accept) {
        Match match = matchService.getById(matchId);
        if (match.getHomeTeamId() != null && match.getHomeTeamId().equals(teamId)) {
            throw new ConflictException("该球队已经是主队");
        }
        if (match.getAwayTeamId() != null && match.getAwayTeamId().equals(teamId)) {
            throw new ConflictException("该球队已经是客队");
        }
        if (match.getHomeTeamId() != null && match.getAwayTeamId() != null) {
            throw new ConflictException("该比赛已经有两支球队参加");
        }
        String status = accept ? MatchTeamRequest.STATUS_ACCEPTED : MatchTeamRequest.STATUS_REJECTED;
        MatchTeamRequest matchTeamRequest = new MatchTeamRequest(teamId, matchId, null, status);
        if (matchTeamRequestService.selectByMultiId(matchTeamRequest) == null) {
            throw new BadRequestException("该球队没有收到该比赛的邀请");
        }
        if (!matchTeamRequestService.saveOrUpdateRequestWithTime(matchTeamRequest)) {
            throw new RuntimeException("回复比赛邀请失败");
        }
        if (match.getHomeTeamId() != null && matchTeamRequest.getType().equals(MatchTeamRequest.TYPE_HOME)) {
            throw new ConflictException("已经有另一只球队作为主队");
        }
        if (match.getAwayTeamId() != null && matchTeamRequest.getType().equals(MatchTeamRequest.TYPE_AWAY)) {
            throw new ConflictException("已经有另一只球队作为客队");
        }
        if (accept) {
            if (matchTeamRequest.getType().equals(MatchTeamRequest.TYPE_HOME)) {
                match.setHomeTeamId(teamId);
            } else if (matchTeamRequest.getType().equals(MatchTeamRequest.TYPE_AWAY)) {
                match.setAwayTeamId(teamId);
            } else {
                throw new RuntimeException("比赛邀请类型错误，未明确作为主队或客队");
            }
            if (!matchService.updateById(match)) {
                throw new RuntimeException("更新比赛失败");
            }
        }
        return true;
    }

    @Override
    public List<Match> getMatches(Long teamId) {
        QueryWrapper<Match> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("home_team_id", teamId).or().eq("away_team_id", teamId);
        List<Match> matchList = matchService.list(queryWrapper);
        matchList = matchList.stream()
                .sorted(Comparator.comparing(Match::getTime))
                .peek(match -> {
                    match.setMatchEvent(matchService.findMatchEvent(match));
                    match.setHomeTeam(getById(match.getHomeTeamId()));
                    match.setAwayTeam(getById(match.getAwayTeamId()));
                })
                .toList();
        return matchList;
    }

    @Override
    public boolean requestJoinEvent(EventTeam eventTeam) {
        if (eventTeamService.selectByMultiId(eventTeam) != null) {
            throw new ConflictException("该球队已经参加该赛事");
        }
        EventTeamRequest eventTeamRequest = new EventTeamRequest(
                eventTeam.getEventId(), eventTeam.getTeamId(), EventTeamRequest.TYPE_APPLICATION);
        if (eventTeamRequestService.selectByMultiId(eventTeamRequest) != null) {
            throw new ConflictException("曾经向该赛事发出过申请，请勿重复发送");
        }
        if (!eventTeamRequestService.saveOrUpdateRequestWithTime(eventTeamRequest)) {
            throw new RuntimeException("申请参加赛事失败");
        }
        return true;
    }

    @Override
    public List<EventTeamRequest> getEventInvitations(Long teamId) {
        return eventTeamRequestService.listWithEvent(teamId, EventTeamRequest.TYPE_INVITATION);
    }

    @Override
    @Transactional
    public boolean replyEventInvitation(Long teamId, Long eventId, Boolean accept) {
        EventTeam eventTeam = new EventTeam(eventId, teamId);
        if (eventTeamService.selectByMultiId(eventTeam) != null) {
            throw new ConflictException("该球队已经参加该赛事");
        }
        String status = accept ? EventTeamRequest.STATUS_ACCEPTED : EventTeamRequest.STATUS_REJECTED;
        EventTeamRequest eventTeamRequest = new EventTeamRequest(eventId, teamId,
                EventTeamRequest.TYPE_INVITATION, status);
        if (eventTeamRequestService.selectByMultiId(eventTeamRequest) == null) {
            throw new BadRequestException("该球队没有收到该赛事的邀请");
        }
        if (!eventTeamRequestService.saveOrUpdateRequestWithTime(eventTeamRequest)) {
            throw new RuntimeException("回复赛事邀请失败");
        }
        if (accept) {
            if (!eventTeamService.saveOrUpdateByMultiId(eventTeam)) {
                throw new RuntimeException("参加赛事失败");
            }
        }
        return true;
    }

    @Override
    public List<Event> getEvents(Long teamId) {
        return eventTeamService
                .listWithEvent(teamId)
                .stream()
                .map(EventTeam::getEvent)
                .toList();
    }

    @Override
    public boolean addUniform(TeamUniform teamUniform) {
        if (!teamUniformService.saveOrUpdateByMultiId(teamUniform)) {
            throw new BadRequestException("添加队服失败");
        }
        return true;
    }

    @Override
    public List<TeamUniform> getUniforms(Long teamId) {
        QueryWrapper<TeamUniform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        return teamUniformService.list(queryWrapper);
    }

    @Override
    public boolean deleteUniform(TeamUniform teamUniform) {
        if (!teamUniformService.deleteByMultiId(teamUniform)) {
            throw new BadRequestException("删除队服失败");
        }
        return true;
    }
}