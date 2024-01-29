
package com.sustech.football.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

public interface TeamService extends IService<Team> {
    void inviteManager(TeamManager teamManager);
    List<Long> getManagers(Long teamId);
    void deleteManager(TeamManager teamManager);

    void invitePlayer(TeamPlayer teamPlayer);
    List<TeamPlayerRequest> getPlayerApplications(Long teamId);
    void replyPlayerApplication(Long teamId, Long playerId, Boolean accept);
    List<Player> getPlayers(Long teamId);
    void deletePlayer(TeamPlayer teamPlayer);

    void inviteCoach(TeamCoach teamCoach);
    List<Coach> getCoaches(Long teamId);
    void deleteCoach(TeamCoach teamCoach);

    List<MatchTeamRequest> getMatchInvitations(Long teamId);
    void replyMatchInvitation(Long teamId, Long matchId, Boolean accept);
    List<Match> getMatches(Long teamId);

    void requestJoinEvent(EventTeam eventTeam);
    List<EventTeamRequest> getEventInvitations(Long teamId);
    void replyEventInvitation(Long teamId, Long eventId, Boolean accept);
    List<Event> getEvents(Long teamId);

    void addUniform(TeamUniform teamUniform);
    List<TeamUniform> getUniforms(Long teamId);
    void deleteUniform(TeamUniform teamUniform);
}