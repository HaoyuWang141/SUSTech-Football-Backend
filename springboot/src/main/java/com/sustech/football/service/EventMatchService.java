
package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.EventMatch;

import java.util.List;

public interface EventMatchService extends IMppService<EventMatch> {
    List<EventMatch> listWithMatch(Long eventId);
}