
package com.sustech.football.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

public interface TeamService extends IService<Team> {
    Team getTeamById(Long teamId);

    boolean deleteTeam(Long teamId, Long userId);

    List<Team> getTeamsByIdList(List<Long> teamIdList);

    boolean inviteManager(TeamManager teamManager);

    List<Long> getManagers(Long teamId);

    boolean deleteManager(TeamManager teamManager);

    boolean invitePlayer(TeamPlayer teamPlayer);

    List<TeamPlayerRequest> getPlayerInvitations(Long teamId);

    List<TeamPlayerRequest> getPlayerApplications(Long teamId);

    boolean replyPlayerApplication(Long teamId, Long playerId, Boolean accept);

    List<TeamPlayer> getTeamPlayers(Long teamId);

    List<Player> getPlayers(Long teamId);

    boolean retirePlayer(Long teamId, Long playerId);

    boolean rehirePlayer(Long teamId, Long playerId, Integer number);

    boolean deletePlayer(Long teamId, Long playerId);

    boolean inviteCoach(TeamCoach teamCoach);

    List<Coach> getCoaches(Long teamId);

    boolean deleteCoach(TeamCoach teamCoach);

    List<MatchTeamRequest> getMatchInvitations(Long teamId);

    boolean replyMatchInvitation(Long teamId, Long matchId, Boolean accept);

    List<Match> getMatches(Long teamId);

    List<Match> getFriendlyMatches(Long teamId);

    boolean requestJoinEvent(EventTeam eventTeam);

    List<EventTeamRequest> getEventInvitations(Long teamId);

    boolean replyEventInvitation(Long teamId, Long eventId, Boolean accept);

    List<Event> getEvents(Long teamId);

    boolean addUniform(TeamUniform teamUniform);

    List<TeamUniform> getUniforms(Long teamId);

    boolean deleteUniform(TeamUniform teamUniform);

    boolean updateCaptainByPlayerId(Long teamId, Long playerId);

    List<Team> getAllTeams();
}