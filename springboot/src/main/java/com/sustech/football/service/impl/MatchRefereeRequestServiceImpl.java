package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchRefereeRequest;
import com.sustech.football.mapper.MatchRefereeRequestMapper;
import com.sustech.football.service.MatchRefereeRequestService;
import org.springframework.stereotype.Service;

@Service
public class MatchRefereeRequestServiceImpl extends MppServiceImpl<MatchRefereeRequestMapper, MatchRefereeRequest> implements MatchRefereeRequestService {
}
