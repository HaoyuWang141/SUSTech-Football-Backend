package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.MatchRefereeRequest;
import com.sustech.springboot.mapper.MatchRefereeRequestMapper;
import com.sustech.springboot.service.MatchRefereeRequestService;
import org.springframework.stereotype.Service;

@Service
public class MatchRefereeRequestServiceImpl extends MppServiceImpl<MatchRefereeRequestMapper, MatchRefereeRequest> implements MatchRefereeRequestService {
}
