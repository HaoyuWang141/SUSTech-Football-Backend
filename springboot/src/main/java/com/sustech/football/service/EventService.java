
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface EventService extends IService<Event> {
    Event getDetailedEvent(Long eventId);

    boolean inviteManager(EventManager eventManager);

    List<Long> getManagers(Long eventId);

    boolean deleteManager(EventManager eventManager);

    boolean inviteTeam(EventTeamRequest eventTeamRequest);

    List<EventTeamRequest> getTeamInvitations(Long eventId);

    List<EventTeamRequest> getTeamApplications(Long eventId);

    boolean replyTeamApplication(EventTeamRequest eventTeamRequest);

    List<Team> getTeams(Long eventId);

    boolean deleteTeam(EventTeam eventTeam);

    boolean addMatch(Long eventId, Match match, String stage, String tag);

    List<Match> getMatches(Long eventId);

    boolean deleteMatch(EventMatch eventMatch);

    boolean inviteReferee(EventRefereeRequest eventRefereeRequest);

    List<Referee> getReferees(Long eventId);

    boolean deleteReferee(EventReferee eventReferee);
}