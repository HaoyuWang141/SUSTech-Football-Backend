
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamCoach;
import com.sustech.football.mapper.TeamCoachMapper;
import com.sustech.football.service.TeamCoachService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachServiceImpl extends MppServiceImpl<TeamCoachMapper, TeamCoach> implements TeamCoachService {
    @Autowired
    private TeamCoachMapper teamCoachMapper;

    @Override
    public List<TeamCoach> listWithTeam(Long coachId) {
        return teamCoachMapper.selectListWithTeam(coachId);
    }

    @Override
    public List<TeamCoach> listWithCoach(Long teamId) {
        return teamCoachMapper.selectListWithCoach(teamId);
    }
}