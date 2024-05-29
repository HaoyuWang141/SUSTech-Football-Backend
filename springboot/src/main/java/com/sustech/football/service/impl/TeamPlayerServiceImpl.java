
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.exception.BadRequestException;
import com.sustech.football.exception.ConflictException;
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

    @Override
    public boolean updatePlayerNumber(Long teamId, Long playerId, Integer number) {
        QueryWrapper<TeamPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId).eq("number", number);
        TeamPlayer numberPlayer = this.getOne(queryWrapper);
        if (numberPlayer != null) {
            throw new ConflictException("号码已被使用");
        }
        TeamPlayer teamPlayer = this.selectByMultiId(new TeamPlayer(teamId, playerId));
        if (teamPlayer == null) {
            throw new BadRequestException("球员不在球队中");
        }
        teamPlayer.setNumber(number);
        if (!this.updateByMultiId(teamPlayer)) {
            throw new RuntimeException("更新号码失败");
        }
        return true;
    }
}