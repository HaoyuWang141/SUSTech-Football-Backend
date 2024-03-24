
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Player;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.mapper.PlayerMapper;
import com.sustech.football.service.MatchService;
import com.sustech.football.service.PlayerService;
import com.sustech.football.service.TeamPlayerService;
import com.sustech.football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerServiceImpl extends ServiceImpl<PlayerMapper, Player> implements PlayerService {
    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchService matchService;

    @Override
    public List<Match> getPlayerMatches(Long playerId) {
        List<TeamPlayer> teamPlayers = teamPlayerService.list(new QueryWrapper<TeamPlayer>().eq("player_id", playerId));
        return teamPlayers.stream()
                .map(TeamPlayer::getTeamId)
                .map(teamService::getMatches)
                .flatMap(List::stream)
                .distinct()
                .sorted(Comparator.comparing(Match::getTime))
                .peek(match -> {
                    match.setMatchEvent(matchService.findMatchEvent(match));
                    match.setHomeTeam(teamService.getById(match.getHomeTeamId()));
                    match.setAwayTeam(teamService.getById(match.getAwayTeamId()));
                })
                .toList();
    }
}