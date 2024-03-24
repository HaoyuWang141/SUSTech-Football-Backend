
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Player;

import java.util.List;

public interface PlayerService extends IService<Player> {
    List<Match> getPlayerMatches(Long playerId);
}