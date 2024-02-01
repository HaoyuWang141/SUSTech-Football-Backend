
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

    @Override
    public List<MatchRefereeRequest> getMatchInvitations(Long refereeId) {
        return matchRefereeRequestService.listWithMatch(refereeId);
    }

    @Override
    @Transactional
    public boolean replyMatchInvitation(MatchRefereeRequest matchRefereeRequest) {
        MatchReferee matchReferee = new MatchReferee(matchRefereeRequest.getMatchId(), matchRefereeRequest.getRefereeId());
        if (matchRefereeService.selectByMultiId(matchReferee) != null) {
            throw new ConflictException("裁判已经加入执法该比赛");
        }
        if (matchRefereeRequestService.selectByMultiId(matchRefereeRequest) == null) {
            throw new BadRequestException("该比赛未邀请该裁判加入");
        }
        if (!matchRefereeRequestService.updateByMultiId(matchRefereeRequest)) {
            throw new RuntimeException("回复比赛邀请失败");
        }
        if (matchRefereeRequest.getStatus().equals(MatchRefereeRequest.STATUS_ACCEPTED)) {
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
                .toList();
    }

    @Override
    public List<EventRefereeRequest> getEventInvitations(Long refereeId) {
        return eventRefereeRequestService.listWithEvent(refereeId);
    }

    @Override
    public boolean replyEventInvitation(EventRefereeRequest eventRefereeRequest) {
        EventReferee eventReferee = new EventReferee(eventRefereeRequest.getEventId(), eventRefereeRequest.getRefereeId());
        if (eventRefereeService.selectByMultiId(eventReferee) != null) {
            throw new ConflictException("裁判已经加入执法该赛事");
        }
        if (eventRefereeRequestService.selectByMultiId(eventRefereeRequest) == null) {
            throw new BadRequestException("该赛事未邀请该裁判加入");
        }
        if (!eventRefereeRequestService.updateByMultiId(eventRefereeRequest)) {
            throw new RuntimeException("回复赛事邀请失败");
        }
        if (eventRefereeRequest.getStatus().equals(EventRefereeRequest.STATUS_ACCEPTED)) {
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