package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamPlayerRequest;
import com.sustech.springboot.mapper.TeamPlayerRequestMapper;
import com.sustech.springboot.service.TeamPlayerRequestService;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerRequestServiceImpl extends MppServiceImpl<TeamPlayerRequestMapper, TeamPlayerRequest> implements TeamPlayerRequestService {
}
