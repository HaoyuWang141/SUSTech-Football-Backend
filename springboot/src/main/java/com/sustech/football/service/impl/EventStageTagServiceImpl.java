package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventStageTag;
import com.sustech.football.mapper.EventStageTagMapper;
import com.sustech.football.service.EventStageTagService;
import org.springframework.stereotype.Service;

@Service
public class EventStageTagServiceImpl extends MppServiceImpl<EventStageTagMapper, EventStageTag> implements EventStageTagService {
}
