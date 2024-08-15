
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Player;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.entity.TeamPlayerRequest;
import com.sustech.football.exception.ConflictException;
import com.sustech.football.exception.InternalServerErrorException;
import com.sustech.football.exception.ResourceNotFoundException;
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
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest();
        teamPlayerRequest.setTeamId(teamId);
        teamPlayerRequest.setPlayerId(playerId);
        teamPlayerRequest.setType(TeamPlayerRequest.TYPE_INVITATION);
        teamPlayerRequest = teamPlayerRequestService.selectByMultiId(teamPlayerRequest);
        if (teamPlayerRequest == null) {
            throw new ConflictException("球队未邀请");
        }

        // 若球员已经加入球队，则将status置为ACCEPTED并直接返回（不能抛异常，因为有@Transaction限制）
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setTeamId(teamId);
        teamPlayer.setPlayerId(playerId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            teamPlayerRequest.setStatus(TeamPlayerRequest.STATUS_ACCEPTED);
            teamPlayerRequestService.updateByMultiId(teamPlayerRequest);
            return false;
        }

        if (!teamPlayerRequest.getStatus().equals(TeamPlayerRequest.STATUS_PENDING)) {
            throw new ConflictException("邀请已处理");
        }

        String status = accept ? TeamPlayerRequest.STATUS_ACCEPTED : TeamPlayerRequest.STATUS_REJECTED;
        teamPlayerRequest.setStatus(status);
        if (!teamPlayerRequestService.updateByMultiId(teamPlayerRequest)) {
            throw new RuntimeException("回应邀请失败");
        }
        if (accept) {
            if (!teamPlayerService.saveOrUpdateByMultiId(teamPlayer)) {
                throw new RuntimeException("加入球队失败");
            }

            // 当球员同意加入球队（回复邀请）时，若还存在球员申请记录状态为PENDING，则将申请自动置为ACCEPTED
            TeamPlayerRequest teamPlayerRequest_application = new TeamPlayerRequest();
            teamPlayerRequest_application.setTeamId(teamId);
            teamPlayerRequest_application.setPlayerId(playerId);
            teamPlayerRequest_application.setType(TeamPlayerRequest.TYPE_APPLICATION);
            teamPlayerRequest_application = teamPlayerRequestService.selectByMultiId(teamPlayerRequest_application);
            if (teamPlayerRequest_application != null && teamPlayerRequest_application.getStatus().equals(TeamPlayerRequest.STATUS_PENDING)) {
                teamPlayerRequest_application.setStatus(TeamPlayerRequest.STATUS_ACCEPTED);
                teamPlayerRequestService.updateByMultiId(teamPlayerRequest_application);
            }
        }

        return true;
    }

    @Override
    public boolean exitTeam(Long playerId, Long teamId) {
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setPlayerId(playerId);
        teamPlayer.setTeamId(teamId);
        if (teamPlayerService.selectByMultiId(teamPlayer) != null) {
            throw new ResourceNotFoundException("球员不在球队中，无法退出");
        }
        if (!teamPlayerService.deleteByMultiId(teamPlayer)) {
            throw new InternalServerErrorException("删除失败");
        }
        return true;
    }

}