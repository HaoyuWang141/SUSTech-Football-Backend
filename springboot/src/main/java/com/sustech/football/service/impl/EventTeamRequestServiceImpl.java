package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventTeamRequest;
import com.sustech.football.mapper.EventTeamRequestMapper;
import com.sustech.football.service.EventTeamRequestService;
import org.springframework.stereotype.Service;

@Service
public class EventTeamRequestServiceImpl extends MppServiceImpl<EventTeamRequestMapper, EventTeamRequest> implements EventTeamRequestService {
}
