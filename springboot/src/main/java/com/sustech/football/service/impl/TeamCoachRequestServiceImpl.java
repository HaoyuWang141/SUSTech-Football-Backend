package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamCoachRequest;
import com.sustech.football.mapper.TeamCoachRequestMapper;
import com.sustech.football.service.TeamCoachRequestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachRequestServiceImpl extends MppServiceImpl<TeamCoachRequestMapper, TeamCoachRequest> implements TeamCoachRequestService {

    private final TeamCoachRequestMapper teamCoachRequestMapper;

    @Autowired
    public TeamCoachRequestServiceImpl(TeamCoachRequestMapper teamCoachRequestMapper) {
        this.teamCoachRequestMapper = teamCoachRequestMapper;
    }

    @Override
    public List<TeamCoachRequest> listWithTeam(Long coachId) {
        return teamCoachRequestMapper.selectListWithTeam(coachId);
    }

    @Override
    public List<TeamCoachRequest> listWithCoach(Long teamId) {
        return teamCoachRequestMapper.selectListWithCoach(teamId);
    }
}
