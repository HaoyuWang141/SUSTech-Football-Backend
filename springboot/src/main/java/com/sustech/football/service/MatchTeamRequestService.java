package com.sustech.football.service;

import java.util.List;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.MatchTeamRequest;

public interface MatchTeamRequestService extends IMppService<MatchTeamRequest> {
    List<MatchTeamRequest> listWithMatch(Long teamId);
    List<MatchTeamRequest> listWithTeam(Long matchId);
}
