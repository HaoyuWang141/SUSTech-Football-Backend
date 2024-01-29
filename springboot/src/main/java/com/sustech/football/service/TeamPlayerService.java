
package com.sustech.football.service;

import java.util.List;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.TeamPlayer;

public interface TeamPlayerService extends IMppService<TeamPlayer> {
    List<TeamPlayer> listWithTeam(Long playerId);
    List<TeamPlayer> listWithPlayer(Long teamId);
}