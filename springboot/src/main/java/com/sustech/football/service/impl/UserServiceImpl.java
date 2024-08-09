
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.*;
import com.sustech.football.exception.ResourceNotFoundException;
import com.sustech.football.mapper.*;
import com.sustech.football.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserRoleMapper userRoleMapper;
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
    @Autowired
    private TeamPlayerMapper teamPlayerMapper;
    @Autowired
    private MatchMapper matchMapper;


    @Override
    public List<Match> getUserManageMatches(Long userId) {
        QueryWrapper<TeamManager> teamManagerQueryWrapper = new QueryWrapper<>();
        teamManagerQueryWrapper.eq("user_id", userId);
        List<Team> teamList = teamManagerMapper.selectTeamWithManager(userId).stream().map(TeamManager::getTeam).toList();

        List<Match> matches = new ArrayList<>();
        QueryWrapper<Match> matchQueryWrapper;
        for (Team team : teamList) {
            matchQueryWrapper = new QueryWrapper<>();
            matchQueryWrapper.eq("home_team_id", team.getTeamId()).or().eq("away_team_id", team.getTeamId());
            List<Match> subMatches = matchMapper.selectList(matchQueryWrapper);
            for (Match match : subMatches) {
                match.setHomeTeam(teamMapper.selectById(match.getHomeTeamId()));
                match.setAwayTeam(teamMapper.selectById(match.getAwayTeamId()));
            }
            matches.addAll(subMatches);
        }

        return matches.stream().sorted(Comparator.comparing(Match::getTime).reversed()).toList();
    }

    @Override
    public List<Event> getUserManageEvents(Long userId) {
        return eventManagerMapper.selectEventWithManager(userId)
                .stream()
                .map(EventManager::getEvent)
                .sorted(Comparator.comparing(Event::getEventId))
                .toList();
    }

    @Override
    public List<Team> getUserManageTeams(Long userId) {
        List<Team> teams = teamManagerMapper.selectTeamWithManager(userId).stream().map(TeamManager::getTeam).toList();
        for (Team team : teams) {
            List<TeamPlayer> teamPlayers = teamPlayerMapper.selectListWithPlayer(team.getTeamId());
            team.setPlayerList(teamPlayers.stream().map(TeamPlayer::getPlayer).toList());
        }
        return teams.stream().sorted(Comparator.comparing(Team::getTeamId)).toList();
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

    @Override
    public List<UserRole> getAllRoleUsers() {
        return userRoleMapper.selectRoleUser();
    }

    @Override
    public UserRole getRoleUserById(Long userId) {
        return userRoleMapper.selectRoleUserById(userId);
    }
}