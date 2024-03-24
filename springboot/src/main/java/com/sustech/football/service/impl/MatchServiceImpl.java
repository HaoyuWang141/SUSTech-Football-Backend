
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.mapper.MatchMapper;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements MatchService {
    @Autowired
    private MatchManagerService matchManagerService;
    @Autowired
    private MatchRefereeService matchRefereeService;
    @Autowired
    private MatchRefereeRequestService matchRefereeRequestService;
    @Autowired
    private MatchTeamRequestService matchTeamRequestService;
    @Autowired
    private MatchPlayerActionService matchPlayerActionService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired
    private EventMatchService eventMatchService;
    @Autowired
    private EventService eventService;

    @Override
    public Match getMatch(Long matchId) {
        Match match = getById(matchId);
        if (match == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (match.getHomeTeamId() != null) {
            Team homeTeam = teamService.getById(match.getHomeTeamId());
            homeTeam.setPlayerList(teamPlayerService.listWithPlayer(homeTeam.getTeamId()).stream().map(TeamPlayer::getPlayer).toList());
            match.setHomeTeam(homeTeam);
        }
        if (match.getAwayTeamId() != null) {
            Team awayTeam = teamService.getById(match.getAwayTeamId());
            awayTeam.setPlayerList(teamPlayerService.listWithPlayer(awayTeam.getTeamId()).stream().map(TeamPlayer::getPlayer).toList());
            match.setAwayTeam(awayTeam);
        }
        match.setRefereeList(matchRefereeService.listWithReferee(matchId)
                .stream()
                .map(MatchReferee::getReferee)
                .toList());
        match.setMatchPlayerActionList(matchPlayerActionService.list(new QueryWrapper<MatchPlayerAction>().eq("match_id", matchId)));
        match.setMatchEvent(this.findMatchEvent(match));
        return match;
    }

    @Override
    public List<Match> getMatchByIdList(List<Long> matchIdList) {
        return matchIdList.stream().map(this::getMatch).toList();
    }

    @Override
    public MatchEvent findMatchEvent(Match match) {
        return new MatchEvent(eventMatchService.getEventMatchByMatch(match.getMatchId()));
    }

    public List<Match> getAllMatches() {
        List<Match> matches = list();
        for (Match match : matches) {
            if (match.getHomeTeamId() != null) {
                Team homeTeam = teamService.getById(match.getHomeTeamId());
                match.setHomeTeam(homeTeam);
            }
            if (match.getAwayTeamId() != null) {
                Team awayTeam = teamService.getById(match.getAwayTeamId());
                match.setAwayTeam(awayTeam);
            }
        }

        return matches;
    }

    @Override
    public boolean inviteManager(MatchManager matchManager) {
        if (matchManagerService.selectByMultiId(matchManager) != null) {
            throw new ConflictException("该用户已经是该比赛的管理员");
        }
        if (!matchManagerService.saveOrUpdateByMultiId(matchManager)) {
            throw new RuntimeException("邀请失败");
        }
        return true;
    }

    @Override
    public List<Long> getManagers(Long matchId) {
        QueryWrapper<MatchManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId);
        return matchManagerService.list(queryWrapper).stream().map(MatchManager::getUserId).toList();
    }

    @Override
    public boolean deleteManager(MatchManager matchManager) {
        if (!matchManagerService.deleteByMultiId(matchManager)) {
            throw new BadRequestException("删除管理员失败");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean inviteTeam(MatchTeamRequest matchTeamRequest) {
        Match match = getById(matchTeamRequest.getMatchId());
        if (match.getHomeTeamId().equals(matchTeamRequest.getTeamId())
                || match.getAwayTeamId().equals(matchTeamRequest.getTeamId())) {
            throw new ConflictException("该队伍已经是该比赛的队伍");
        }
        if (matchTeamRequestService.selectByMultiId(matchTeamRequest) != null) {
            throw new ConflictException("该队伍已经被邀请");
        }
        if (!matchTeamRequestService.updateByMultiId(matchTeamRequest)) {
            throw new RuntimeException("邀请失败");
        }
        return true;
    }

    @Override
    public List<MatchTeamRequest> getTeamInvitations(Long teamId) {
        return matchTeamRequestService.listWithMatch(teamId);
    }

    @Override
    public boolean deleteTeam(Long matchId, Boolean isHomeTeam) {
        Match match = getById(matchId);
        if (match == null) {
            throw new ResourceNotFoundException("比赛不存在");
        }
        if (isHomeTeam) {
            if (match.getHomeTeamId() == null) {
                throw new BadRequestException("主队不存在");
            }
            match.setHomeTeamId(null);
        } else {
            if (match.getAwayTeamId() == null) {
                throw new BadRequestException("客队不存在");
            }
            match.setAwayTeamId(null);
        }
        if (!updateById(match)) {
            throw new BadRequestException("删除队伍失败");
        }
        return false;
    }

    @Override
    public boolean inviteReferee(MatchReferee matchReferee) {
        if (matchRefereeService.selectByMultiId(matchReferee) != null) {
            throw new ConflictException("该用户已经是该比赛的裁判");
        }
        MatchRefereeRequest matchRefereeRequest = new MatchRefereeRequest(
                matchReferee.getMatchId(),
                matchReferee.getRefereeId());
        if (matchRefereeRequestService.selectByMultiId(matchRefereeRequest) != null) {
            throw new ConflictException("已经邀请过该裁判，请勿重复发送");
        }
        if (!matchRefereeRequestService.saveOrUpdateRequestWithTime(matchRefereeRequest)) {
            throw new RuntimeException("邀请裁判失败");
        }
        return true;
    }

    @Override
    public List<Referee> getReferees(Long matchId) {
        return matchRefereeService.listWithReferee(matchId)
                .stream()
                .map(MatchReferee::getReferee)
                .toList();
    }

    @Override
    public boolean deleteReferee(MatchReferee matchReferee) {
        if (matchRefereeService.selectByMultiId(matchReferee) == null) {
            throw new ResourceNotFoundException("比赛未含有该裁判，删除失败");
        }
        if (!matchRefereeService.deleteByMultiId(matchReferee)) {
            throw new RuntimeException("删除裁判失败");
        }
        return true;
    }

    @Override
    public boolean updateResult(Long refereeId, Match match) {
        List<Long> refereeList = matchRefereeService
                .listWithReferee(match.getMatchId())
                .stream()
                .map(MatchReferee::getRefereeId)
                .toList();
        if (!refereeList.contains(refereeId)) {
            throw new BadRequestException("裁判不在本场比赛的执法队列中，无法修改结果");
        }
        if (!updateById(match)) {
            throw new RuntimeException("修改比赛结果失败");
        }
        return true;
    }

    @Override
    public boolean addPlayerAction(Long refereeId, MatchPlayerAction matchPlayerAction) {
        List<Long> refereeList = matchRefereeService
                .listWithReferee(matchPlayerAction.getMatchId())
                .stream()
                .map(MatchReferee::getRefereeId)
                .toList();
        if (!refereeList.contains(refereeId)) {
            throw new BadRequestException("裁判不在本场比赛的执法队列中，无法修改结果");
        }
        if (!matchPlayerActionService.saveOrUpdateByMultiId(matchPlayerAction)) {
            throw new RuntimeException("增加本场比赛的球员行为失败");
        }
        return true;
    }

    @Override
    public Event getEvent(Long matchId) {
        EventMatch eventMatch = eventMatchService.getOne(new QueryWrapper<EventMatch>().eq("match_id", matchId));
        return eventService.getDetailedEvent(eventMatch.getEventId());
    }
}