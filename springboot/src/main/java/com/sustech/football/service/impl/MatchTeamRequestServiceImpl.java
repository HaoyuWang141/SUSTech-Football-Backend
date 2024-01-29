package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchTeamRequest;
import com.sustech.football.mapper.MatchTeamRequestMapper;
import com.sustech.football.service.MatchTeamRequestService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MatchTeamRequestServiceImpl extends MppServiceImpl<MatchTeamRequestMapper, MatchTeamRequest> implements MatchTeamRequestService {

    @Override
    public List<MatchTeamRequest> listWithMatch(Long teamId) {
        return baseMapper.listWithMatch(teamId);
    }

    @Override
    public List<MatchTeamRequest> listWithTeam(Long matchId) {
        return baseMapper.listWithTeam(matchId);
    }
}
