package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventTeamRequest;
import com.sustech.springboot.mapper.EventTeamRequestMapper;
import com.sustech.springboot.service.EventTeamRequestService;
import org.springframework.stereotype.Service;

@Service
public class EventTeamRequestServiceImpl extends MppServiceImpl<EventTeamRequestMapper, EventTeamRequest> implements EventTeamRequestService {
}
