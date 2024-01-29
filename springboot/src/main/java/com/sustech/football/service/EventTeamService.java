
package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.EventTeam;

import java.util.List;

public interface EventTeamService extends IMppService<EventTeam> {
    List<EventTeam> listWithEvent(Long teamId);
    List<EventTeam> listWithTeam(Long eventId);
}