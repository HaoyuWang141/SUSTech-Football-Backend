package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.EventTeamRequest;

import java.util.List;

public interface EventTeamRequestService extends IMppService<EventTeamRequest> {
    List<EventTeamRequest> listWithEvent(Long teamId, String type);
    List<EventTeamRequest> listWithTeam(Long eventId, String type);

    boolean saveOrUpdateRequestWithTime(EventTeamRequest eventTeamRequest);
}
