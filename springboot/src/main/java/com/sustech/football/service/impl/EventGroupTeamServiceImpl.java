package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventGroupTeam;
import com.sustech.football.mapper.EventGroupTeamMapper;
import com.sustech.football.service.EventGroupTeamService;
import org.springframework.stereotype.Service;

@Service
public class EventGroupTeamServiceImpl extends MppServiceImpl<EventGroupTeamMapper, EventGroupTeam> implements EventGroupTeamService {
}
