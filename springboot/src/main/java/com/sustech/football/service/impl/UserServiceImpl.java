
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.mapper.UserMapper;
import com.sustech.football.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService 
{
    @Override
    public boolean favoriteTeam(Long userId, Long teamId) {
        return false;
    }

    @Override
    public boolean unfavoriteTeam(Long userId, Long teamId) {
        return false;
    }

    @Override
    public List<Team> getFavoriteTeams(Long userId) {
        return null;
    }

    @Override
    public boolean favoritePlayer(Long userId, Long playerId) {
        return false;
    }

    @Override
    public boolean unfavoritePlayer(Long userId, Long playerId) {
        return false;
    }

    @Override
    public List<Player> getFavoritePlayers(Long userId) {
        return null;
    }

    @Override
    public boolean favoriteMatch(Long userId, Long matchId) {
        return false;
    }

    @Override
    public boolean unfavoriteMatch(Long userId, Long matchId) {
        return false;
    }

    @Override
    public List<Match> getFavoriteMatches(Long userId) {
        return null;
    }

    @Override
    public boolean favoriteEvent(Long userId, Long eventId) {
        return false;
    }

    @Override
    public boolean unfavoriteEvent(Long userId, Long eventId) {
        return false;
    }

    @Override
    public List<Event> getFavoriteEvents(Long userId) {
        return null;
    }
}