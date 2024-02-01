
package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.*;

import java.util.List;

public interface RefereeService extends IService<Referee> {
    List<MatchRefereeRequest> getMatchInvitations(Long refereeId);

    boolean replyMatchInvitation(MatchRefereeRequest matchRefereeRequest);

    List<Match> getMatches(Long refereeId);

    List<EventRefereeRequest> getEventInvitations(Long refereeId);

    boolean replyEventInvitation(EventRefereeRequest eventRefereeRequest);

    List<Event> getEvents(Long refereeId);
}