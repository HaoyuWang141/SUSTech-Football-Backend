
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventMatch;
import com.sustech.football.entity.MatchEvent;
import com.sustech.football.mapper.EventMatchMapper;
import com.sustech.football.service.EventMatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventMatchServiceImpl extends MppServiceImpl<EventMatchMapper, EventMatch> implements EventMatchService {
    @Override
    public List<EventMatch> listWithMatch(Long eventId) {
        List<EventMatch> eventMatches = baseMapper.selectListWithMatch(eventId);
        for (EventMatch em : eventMatches) {
            em.getMatch().setMatchEvent(new MatchEvent(em));
        }
        return eventMatches;
    }
}