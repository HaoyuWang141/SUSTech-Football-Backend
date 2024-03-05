package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventStage;
import com.sustech.football.mapper.EventStageMapper;
import com.sustech.football.service.EventStageService;
import org.springframework.stereotype.Service;

@Service
public class EventStageServiceImpl extends MppServiceImpl<EventStageMapper, EventStage> implements EventStageService {
}
