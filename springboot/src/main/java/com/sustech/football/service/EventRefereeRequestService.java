package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.EventRefereeRequest;

import java.util.List;

public interface EventRefereeRequestService extends IMppService<EventRefereeRequest> {
    List<EventRefereeRequest> listWithEvent(Long refereeId);
    List<EventRefereeRequest> listWithReferee(Long eventId);
}
