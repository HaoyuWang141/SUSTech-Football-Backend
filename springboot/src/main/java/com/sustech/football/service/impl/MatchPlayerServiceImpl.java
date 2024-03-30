package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchPlayer;
import com.sustech.football.mapper.MatchPlayerMapper;
import com.sustech.football.service.MatchPlayerService;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayerServiceImpl extends MppServiceImpl<MatchPlayerMapper, MatchPlayer> implements MatchPlayerService {
}
