package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchManager;
import com.sustech.football.mapper.MatchManagerMapper;
import com.sustech.football.service.MatchManagerService;
import org.springframework.stereotype.Service;

@Service
public class MatchManagerServiceImpl extends MppServiceImpl<MatchManagerMapper, MatchManager> implements MatchManagerService {
}
