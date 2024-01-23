package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamCoachRequest;
import com.sustech.springboot.mapper.TeamCoachRequestMapper;
import com.sustech.springboot.service.TeamCoachRequestService;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachRequestServiceImpl extends MppServiceImpl<TeamCoachRequestMapper, TeamCoachRequest> implements TeamCoachRequestService {
}
