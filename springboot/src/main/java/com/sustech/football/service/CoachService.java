
package com.sustech.football.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.Coach;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.Match;
import com.sustech.football.entity.Team;

public interface CoachService extends IService<Coach> {
    void replyTeamInvitation(Long teamId, Long coachId, Boolean accept);

    List<Team> getTeams(Long coachId);

    List<Match> getMatches(Long coachId);

    List<Event> getEvents(Long coachId);
}