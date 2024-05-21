package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface MatchService extends IService<Match> {
    Match getMatch(Long matchId);

    List<Match> getMatchByIdList(List<Long> matchIdList);

    List<Match> getAllMatches();

    boolean deleteMatch(Long matchId,Long userId);

    MatchEvent findMatchEvent(Match match);

    boolean inviteManager(MatchManager matchManager);

    List<Long> getManagers(Long matchId);

    boolean deleteManager(MatchManager matchManager);

    boolean inviteTeam(MatchTeamRequest matchTeamRequest);

    List<MatchTeamRequest> getTeamInvitations(Long matchId);

    boolean deleteTeam(Long matchId, Boolean isHomeTeam);

    boolean inviteReferee(MatchReferee matchReferee);

    List<Referee> getReferees(Long matchId);

    boolean deleteReferee(MatchReferee matchReferee);

    boolean updateResult(Long refereeId, Match match);

    boolean addPlayerAction(Long refereeId, MatchPlayerAction matchPlayerAction);

    Event getEvent(Long matchId);

    List<MatchPlayerAction> getMatchPlayerActions(Long matchId);

    boolean deletePlayerAction(Long refereeId, MatchPlayerAction matchPlayerAction);
}