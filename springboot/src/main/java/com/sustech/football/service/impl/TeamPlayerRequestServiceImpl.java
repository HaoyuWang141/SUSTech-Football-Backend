package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamPlayerRequest;
import com.sustech.football.mapper.TeamPlayerRequestMapper;
import com.sustech.football.service.TeamPlayerRequestService;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerRequestServiceImpl extends MppServiceImpl<TeamPlayerRequestMapper, TeamPlayerRequest> implements TeamPlayerRequestService {
}
