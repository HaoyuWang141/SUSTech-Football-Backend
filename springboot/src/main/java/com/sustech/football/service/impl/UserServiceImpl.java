
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.mapper.*;
import com.sustech.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private MatchManagerMapper matchManagerMapper;
    @Autowired
    private EventManagerMapper eventManagerMapper;
    @Autowired
    private TeamManagerMapper teamManagerMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private RefereeMapper refereeMapper;


    @Override
    public List<Match> getUserManageMatches(Long userId) {
        List<Match> matches = matchManagerMapper.selectMatchWithManager(userId).stream().map(MatchManager::getMatch).toList();
        for (Match match : matches) {
            match.setAwayTeam(teamMapper.selectById(match.getAwayTeamId()));
            match.setHomeTeam(teamMapper.selectById(match.getHomeTeamId()));
        }

        return matches;
    }

    @Override
    public List<Event> getUserManageEvents(Long userId) {
        List<Event> events = eventManagerMapper.selectEventWithManager(userId).stream().map(EventManager::getEvent).toList();
        return events;
    }

    @Override
    public List<Team> getUserManageTeams(Long userId) {
        List<Team> teams = teamManagerMapper.selectTeamWithManager(userId).stream().map(TeamManager::getTeam).toList();
        return teams;
    }

    @Override
    public Long getPlayerId(Long userId) {
        Player player = playerMapper.selectOne(new QueryWrapper<Player>().eq("user_id", userId));
        if (player == null) {
            throw new ResourceNotFoundException("用户未注册球员");
        }
        return player.getPlayerId();
    }

    @Override
    public Long getCoachId(Long userId) {
        Coach coach = coachMapper.selectOne(new QueryWrapper<Coach>().eq("user_id", userId));
        if (coach == null) {
            throw new ResourceNotFoundException("用户未注册教练");
        }
        return coach.getCoachId();
    }

    @Override
    public Long getRefereeId(Long userId) {
        Referee referee = refereeMapper.selectOne(new QueryWrapper<Referee>().eq("user_id", userId));
        if (referee == null) {
            throw new ResourceNotFoundException("用户未注册裁判");
        }
        return referee.getRefereeId();
    }
}