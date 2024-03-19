package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamPlayerRequest;
import com.sustech.football.mapper.TeamPlayerRequestMapper;
import com.sustech.football.service.TeamPlayerRequestService;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerRequestServiceImpl extends MppServiceImpl<TeamPlayerRequestMapper, TeamPlayerRequest> implements TeamPlayerRequestService {

    private final TeamPlayerRequestMapper teamPlayerRequestMapper;

    @Autowired
    public TeamPlayerRequestServiceImpl(TeamPlayerRequestMapper teamPlayerRequestMapper) {
        this.teamPlayerRequestMapper = teamPlayerRequestMapper;
    }

    @Override
    public List<TeamPlayerRequest> listWithTeam(Long playerId, String type) {
        return teamPlayerRequestMapper.selectListWithTeam(playerId, type);
    }

    @Override
    public List<TeamPlayerRequest> listWithPlayer(Long teamId, String type) {
        return teamPlayerRequestMapper.selectListWithPlayer(teamId, type);
    }

    @Override
    public boolean saveOrUpdateRequestWithTime(TeamPlayerRequest teamPlayerRequest) {
        teamPlayerRequest.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        return this.saveOrUpdateByMultiId(teamPlayerRequest);
    }
}
