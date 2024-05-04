package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.Event;
import com.sustech.football.entity.EventStage;

public interface EventStageService extends IMppService<EventStage> {
    boolean updateStageAndTage(Event event);
}
