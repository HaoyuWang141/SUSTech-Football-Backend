
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Player;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.entity.TeamPlayerRequest;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.mapper.PlayerMapper;
import com.sustech.football.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private TeamPlayerRequestService teamPlayerRequestService;

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

    @Override
    @Transactional
    public boolean replyTeamInvitation(Long playerId, Long teamId, Boolean accept) {
        String status = accept ? TeamPlayerRequest.STATUS_ACCEPTED : TeamPlayerRequest.STATUS_REJECTED;
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest();
        teamPlayerRequest.setTeamId(teamId);
        teamPlayerRequest.setPlayerId(playerId);
        teamPlayerRequest.setType(TeamPlayerRequest.TYPE_INVITATION);
        teamPlayerRequest = teamPlayerRequestService.selectByMultiId(teamPlayerRequest);
        if (teamPlayerRequest == null) {
            throw new ConflictException("球员未收到邀请");
        }

        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setTeamId(teamId);
        teamPlayer.setPlayerId(playerId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            teamPlayerRequest.setStatus(TeamPlayerRequest.STATUS_ACCEPTED);
            teamPlayerRequestService.updateByMultiId(teamPlayerRequest);
            throw new ConflictException("球员已经加入球队");
        }

        if (!teamPlayerRequest.getStatus().equals(TeamPlayerRequest.STATUS_PENDING)) {
            throw new ConflictException("邀请已处理");
        }

        teamPlayerRequest.setStatus(status);
        if (!teamPlayerRequestService.updateByMultiId(teamPlayerRequest)) {
            throw new RuntimeException("回应邀请失败");
        }
        if (accept) {
            if (!teamPlayerService.saveOrUpdateByMultiId(teamPlayer)) {
                throw new RuntimeException("加入球队失败");
            }
        }

        return true;
    }
}