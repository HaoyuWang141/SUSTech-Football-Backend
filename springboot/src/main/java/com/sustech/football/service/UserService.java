
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface UserService extends IService<User> {
    boolean favoriteTeam(Long userId, Long teamId);

    boolean unfavoriteTeam(Long userId, Long teamId);

    List<Team> getFavoriteTeams(Long userId);

    boolean favoritePlayer(Long userId, Long playerId);

    boolean unfavoritePlayer(Long userId, Long playerId);

    List<Player> getFavoritePlayers(Long userId);

    boolean favoriteMatch(Long userId, Long matchId);

    boolean unfavoriteMatch(Long userId, Long matchId);

    List<Match> getFavoriteMatches(Long userId);

    boolean favoriteEvent(Long userId, Long eventId);

    boolean unfavoriteEvent(Long userId, Long eventId);

    List<Event> getFavoriteEvents(Long userId);
}