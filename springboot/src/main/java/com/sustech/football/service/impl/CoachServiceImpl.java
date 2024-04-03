
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.mapper.CoachMapper;
import com.sustech.football.service.*;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService {

    @Autowired
    private TeamCoachRequestService teamCoachRequestService;

    @Autowired
    private TeamCoachService teamCoachService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MatchService matchService;

    @Override
    @Transactional
    public boolean replyTeamInvitation(Long teamId, Long coachId, Boolean accept) {
        String status = accept ? TeamCoachRequest.STATUS_ACCEPTED : TeamCoachRequest.STATUS_REJECTED;
        TeamCoachRequest teamCoachRequest = new TeamCoachRequest();
        teamCoachRequest.setTeamId(teamId);
        teamCoachRequest.setCoachId(coachId);
        teamCoachRequest = teamCoachRequestService.selectByMultiId(teamCoachRequest);
        if (teamCoachRequest == null) {
            throw new BadRequestException("教练未收到邀请");
        }

        TeamCoach teamCoach = new TeamCoach();
        teamCoach.setTeamId(teamId);
        teamCoach.setCoachId(coachId);
        if (teamCoachService.selectByMultiId(teamCoach) != null) {
            teamCoachRequest.setStatus(TeamCoachRequest.STATUS_ACCEPTED);
            teamCoachRequestService.updateByMultiId(teamCoachRequest);
            return false;
        }

        if (!teamCoachRequest.getStatus().equals(TeamCoachRequest.STATUS_PENDING)) {
            throw new BadRequestException("邀请已处理");
        }

        teamCoachRequest.setStatus(status);
        if (!teamCoachRequestService.updateByMultiId(teamCoachRequest)) {
            throw new RuntimeException("回应邀请失败");
        }
        if (accept) {
            if (!teamCoachService.saveOrUpdateByMultiId(teamCoach)) {
                throw new RuntimeException("加入球队失败");
            }
        }

        return true;
    }

    @Override
    public List<Team> getTeams(Long coachId) {
        List<TeamCoach> teamCoachList = teamCoachService.listWithTeam(coachId);
        return teamCoachList.stream().map(TeamCoach::getTeam).toList();
    }

    @Override
    public List<Match> getMatches(Long coachId) {
        List<TeamCoach> teamCoachList = teamCoachService.listWithTeam(coachId);
        if (teamCoachList.isEmpty()) {
            return List.of();
        }
        return teamCoachList.stream()
                .map(TeamCoach::getTeamId)
                .map(teamService::getMatches)
                .flatMap(List::stream)
                .distinct()
                .sorted(Comparator.comparing(Match::getTime))
                .peek(match -> {
                    match.setMatchEvent(matchService.findMatchEvent(match));
                    match.setHomeTeam(teamService.getById(match.getHomeTeamId()));
                    match.setAwayTeam(teamService.getById(match.getAwayTeamId()));
                })
                .toList();
    }

    @Override
    public List<Event> getEvents(Long coachId) {
        List<TeamCoach> teamCoachList = teamCoachService.listWithTeam(coachId);
        return teamCoachList.stream()
                .map(TeamCoach::getTeamId)
                .map(teamService::getEvents)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }
}