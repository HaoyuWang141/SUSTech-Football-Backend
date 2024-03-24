package com.sustech.football.service;

import com.sustech.football.entity.*;

import java.util.List;

public interface UserFavoriteService {
    boolean favoriteTeam(Long userId, Long teamId);

    boolean unfavoriteTeam(Long userId, Long teamId);

    List<Team> getFavoriteTeams(Long userId);

    boolean favoriteUser(Long userId, Long favoriteId);

    boolean unfavoriteUser(Long userId, Long favoriteId);

    List<User> getFavoriteUsers(Long userId);

    boolean favoriteMatch(Long userId, Long matchId);

    boolean unfavoriteMatch(Long userId, Long matchId);

    List<Match> getFavoriteMatches(Long userId);

    boolean favoriteEvent(Long userId, Long eventId);

    boolean unfavoriteEvent(Long userId, Long eventId);

    List<Event> getFavoriteEvents(Long userId);

    boolean isFavoriteTeam(Long userId, Long teamId);

    boolean isFavoriteUser(Long userId, Long favoriteId);

    boolean isFavoriteMatch(Long userId, Long matchId);

    boolean isFavoriteEvent(Long userId, Long eventId);
}
