package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamCoachRequest;
import com.sustech.football.mapper.TeamCoachRequestMapper;
import com.sustech.football.service.TeamCoachRequestService;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachRequestServiceImpl extends MppServiceImpl<TeamCoachRequestMapper, TeamCoachRequest> implements TeamCoachRequestService {
}
