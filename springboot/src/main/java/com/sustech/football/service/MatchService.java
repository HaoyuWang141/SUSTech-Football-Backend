package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface MatchService extends IService<Match> {
    Match getMatch(Long matchId);

    List<Match> getMatchByIdList(List<Long> matchIdList);

    List<Match> getAllMatches();

    boolean inviteManager(MatchManager matchManager);

    List<Long> getManagers(Long matchId);

    boolean deleteManager(MatchManager matchManager);

    boolean inviteTeam(MatchTeamRequest matchTeamRequest);

    boolean deleteTeam(Long matchId, Boolean isHomeTeam);

    boolean inviteReferee(MatchReferee matchReferee);

    List<Referee> getReferees(Long matchId);

    boolean deleteReferee(MatchReferee matchReferee);

    boolean updateResult(Long refereeId, Match match);

    boolean addPlayerAction(Long refereeId, MatchPlayerAction matchPlayerAction);

}