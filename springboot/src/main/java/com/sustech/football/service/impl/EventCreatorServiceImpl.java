package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.EventCreator;
import com.sustech.football.mapper.EventCreatorMapper;
import com.sustech.football.service.EventCreatorService;
import org.springframework.stereotype.Service;

@Service
public class EventCreatorServiceImpl extends ServiceImpl<EventCreatorMapper, EventCreator> implements EventCreatorService {
}
