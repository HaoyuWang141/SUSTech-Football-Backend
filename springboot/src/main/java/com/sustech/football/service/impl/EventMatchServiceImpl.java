
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.EventMatch;
import com.sustech.football.entity.MatchEvent;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.mapper.EventMatchMapper;
import com.sustech.football.service.EventMatchService;
import com.sustech.football.service.EventService;
import com.sustech.football.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EventMatchServiceImpl extends MppServiceImpl<EventMatchMapper, EventMatch> implements EventMatchService {

    @Autowired
    MatchService matchService;
    @Autowired
    private EventService eventService;

    @Override
    public List<EventMatch> listWithMatch(Long eventId) {
        List<EventMatch> eventMatches = baseMapper.selectListWithMatch(eventId);
        for (EventMatch em : eventMatches) {
            em.getMatch().setMatchEvent(matchService.findMatchEvent(em.getMatch()));
        }
        return eventMatches;
    }

    @Override
    public EventMatch getEventMatchByMatch(Long matchId) {
        if (matchId == null) {
            throw new BadRequestException("没有提供比赛ID");
        }
        return baseMapper.selectEventMatchWithMatch(matchId);
    }

}