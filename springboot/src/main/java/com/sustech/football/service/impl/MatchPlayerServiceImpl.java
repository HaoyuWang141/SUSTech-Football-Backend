package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchPlayer;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.mapper.MatchPlayerMapper;
import com.sustech.football.service.MatchPlayerService;
import com.sustech.football.service.TeamPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchPlayerServiceImpl extends MppServiceImpl<MatchPlayerMapper, MatchPlayer> implements MatchPlayerService {

    @Autowired
    private TeamPlayerService teamPlayerService;

    @Override
    @Transactional
    public boolean addMatchPlayerByTeam(Long matchId, Long teamId) {
        QueryWrapper<TeamPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        List<TeamPlayer> teamPlayerList = teamPlayerService.list(queryWrapper);
        List<MatchPlayer> matchPlayerList = new ArrayList<>();
        for (TeamPlayer teamPlayer : teamPlayerList) {
            MatchPlayer matchPlayer = new MatchPlayer();
            matchPlayer.setMatchId(matchId);
            matchPlayer.setTeamId(teamId);
            matchPlayer.setPlayerId(teamPlayer.getPlayerId());
            matchPlayer.setNumber(teamPlayer.getNumber());
            matchPlayer.setIsStart(false);
            matchPlayerList.add(matchPlayer);
        }
        if (!this.saveOrUpdateBatchByMultiId(matchPlayerList)) {
            throw new RuntimeException("更新比赛球员信息失败");
        }
        return true;
    }
}
