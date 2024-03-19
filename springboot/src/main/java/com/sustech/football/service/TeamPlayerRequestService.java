package com.sustech.football.service;

import java.util.List;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.TeamPlayerRequest;

public interface TeamPlayerRequestService extends IMppService<TeamPlayerRequest> {
    List<TeamPlayerRequest> listWithTeam(Long playerId, String type);

    List<TeamPlayerRequest> listWithPlayer(Long teamId, String type);

    boolean saveOrUpdateRequestWithTime(TeamPlayerRequest teamPlayerRequest);
}
