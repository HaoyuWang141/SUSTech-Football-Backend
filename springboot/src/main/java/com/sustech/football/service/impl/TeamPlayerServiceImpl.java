
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.mapper.TeamPlayerMapper;
import com.sustech.football.service.TeamPlayerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerServiceImpl extends MppServiceImpl<TeamPlayerMapper, TeamPlayer> implements TeamPlayerService {
    @Autowired
    private TeamPlayerMapper teamPlayerMapper;

    @Override
    public List<TeamPlayer> listWithTeam(Long playerId) {
        return teamPlayerMapper.selectListWithTeam(playerId);
    }

    @Override
    public List<TeamPlayer> listWithPlayer(Long teamId) {
        return teamPlayerMapper.selectListWithPlayer(teamId);
    }
}