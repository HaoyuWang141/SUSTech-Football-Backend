
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface RefereeService extends IService<Referee> {
    List<MatchRefereeRequest> getMatchInvitations(Long refereeId);

    boolean replyMatchInvitation(Long refereeId, Long eventId, Boolean accept);

    List<Match> getMatches(Long refereeId);

    List<EventRefereeRequest> getEventInvitations(Long refereeId);

    boolean replyEventInvitation(Long refereeId, Long eventId, Boolean accept);

    List<Event> getEvents(Long refereeId);
}