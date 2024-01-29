
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventTeam;
import com.sustech.football.mapper.EventTeamMapper;
import com.sustech.football.service.EventTeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTeamServiceImpl extends MppServiceImpl<EventTeamMapper, EventTeam> implements EventTeamService
{
    @Override
    public List<EventTeam> listWithEvent(Long teamId) {
        return baseMapper.selectListWithEvent(teamId);
    }

    @Override
    public List<EventTeam> listWithTeam(Long eventId) {
        return baseMapper.selectListWithTeam(eventId);
    }
}