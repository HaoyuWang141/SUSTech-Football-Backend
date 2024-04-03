
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.mapper.RefereeMapper;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RefereeServiceImpl extends ServiceImpl<RefereeMapper, Referee> implements RefereeService {
    @Autowired
    private MatchRefereeRequestService matchRefereeRequestService;
    @Autowired
    private MatchRefereeService matchRefereeService;
    @Autowired
    private EventRefereeRequestService eventRefereeRequestService;
    @Autowired
    private EventRefereeService eventRefereeService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;

    @Override
    public List<MatchRefereeRequest> getMatchInvitations(Long refereeId) {
        return matchRefereeRequestService
                .listWithMatch(refereeId)
                .stream()
                .peek(request -> request.getMatch().setHomeTeam(teamService.getById(request.getMatch().getHomeTeamId())))
                .peek(request -> request.getMatch().setAwayTeam(teamService.getById(request.getMatch().getAwayTeamId())))
                .toList();
    }

    @Override
    @Transactional
    public boolean replyMatchInvitation(Long refereeId, Long matchId, Boolean accept) {
        String status = accept ? MatchRefereeRequest.STATUS_ACCEPTED : MatchRefereeRequest.STATUS_REJECTED;
        MatchRefereeRequest matchRefereeRequest = new MatchRefereeRequest();
        matchRefereeRequest.setMatchId(matchId);
        matchRefereeRequest.setRefereeId(refereeId);
        matchRefereeRequest = matchRefereeRequestService.selectByMultiId(matchRefereeRequest);
        if (matchRefereeRequest == null) {
            throw new ConflictException("裁判未收到邀请");
        }

        MatchReferee matchReferee = new MatchReferee();
        matchReferee.setMatchId(matchId);
        matchReferee.setRefereeId(refereeId);
        if (matchRefereeService.selectByMultiId(matchReferee) != null) {
            matchRefereeRequest.setStatus(MatchRefereeRequest.STATUS_ACCEPTED);
            matchRefereeRequestService.updateByMultiId(matchRefereeRequest);
            return false;
        }

        if (!matchRefereeRequest.getStatus().equals(MatchRefereeRequest.STATUS_PENDING)) {
            throw new ConflictException("邀请已处理");
        }

        matchRefereeRequest.setStatus(status);
        if (!matchRefereeRequestService.updateByMultiId(matchRefereeRequest)) {
            throw new RuntimeException("回复比赛邀请失败");
        }
        if (accept) {
            if (!matchRefereeService.saveOrUpdateByMultiId(matchReferee)) {
                throw new RuntimeException("加入比赛失败");
            }
        }

        return true;
    }

    @Override
    public List<Match> getMatches(Long refereeId) {
        return matchRefereeService
                .listWithMatch(refereeId)
                .stream()
                .map(MatchReferee::getMatch)
                .peek(match -> match.setHomeTeam(teamService.getById(match.getHomeTeamId())))
                .peek(match -> match.setAwayTeam(teamService.getById(match.getAwayTeamId())))
                .peek(match -> match.setMatchEvent(matchService.findMatchEvent(match)))
                .toList();
    }

    @Override
    public List<EventRefereeRequest> getEventInvitations(Long refereeId) {
        return eventRefereeRequestService.listWithEvent(refereeId);
    }

    @Override
    @Transactional
    public boolean replyEventInvitation(Long refereeId, Long eventId, Boolean accept) {
        String status = accept ? EventRefereeRequest.STATUS_ACCEPTED : EventRefereeRequest.STATUS_REJECTED;
        EventRefereeRequest eventRefereeRequest = new EventRefereeRequest();
        eventRefereeRequest.setEventId(eventId);
        eventRefereeRequest.setRefereeId(refereeId);
        eventRefereeRequest = eventRefereeRequestService.selectByMultiId(eventRefereeRequest);
        if (eventRefereeRequest == null) {
            throw new ConflictException("裁判未收到邀请");
        }

        EventReferee eventReferee = new EventReferee();
        eventReferee.setEventId(eventId);
        eventReferee.setRefereeId(refereeId);
        if (eventRefereeService.selectByMultiId(eventReferee) != null) {
            eventRefereeRequest.setStatus(EventRefereeRequest.STATUS_ACCEPTED);
            eventRefereeRequestService.updateByMultiId(eventRefereeRequest);
            return false;
        }

        if (!eventRefereeRequest.getStatus().equals(EventRefereeRequest.STATUS_PENDING)) {
            throw new ConflictException("邀请已处理");
        }

        eventRefereeRequest.setStatus(status);
        if (!eventRefereeRequestService.updateByMultiId(eventRefereeRequest)) {
            throw new RuntimeException("回复赛事邀请失败");
        }
        if (accept) {
            if (!eventRefereeService.saveOrUpdateByMultiId(eventReferee)) {
                throw new RuntimeException("加入赛事失败");
            }
        }

        return true;
    }

    @Override
    public List<Event> getEvents(Long refereeId) {
        return eventRefereeService
                .listWithEvent(refereeId)
                .stream()
                .map(EventReferee::getEvent)
                .toList();
    }
}