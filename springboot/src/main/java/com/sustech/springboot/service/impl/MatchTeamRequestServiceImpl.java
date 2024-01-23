package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.MatchTeamRequest;
import com.sustech.springboot.mapper.MatchTeamRequestMapper;
import com.sustech.springboot.service.MatchTeamRequestService;
import org.springframework.stereotype.Service;

@Service
public class MatchTeamRequestServiceImpl extends MppServiceImpl<MatchTeamRequestMapper, MatchTeamRequest> implements MatchTeamRequestService {
}
