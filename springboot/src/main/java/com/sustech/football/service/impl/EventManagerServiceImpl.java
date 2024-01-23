
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventManager;
import com.sustech.football.mapper.EventManagerMapper;
import com.sustech.football.service.EventManagerService;
import org.springframework.stereotype.Service;

@Service
public class EventManagerServiceImpl extends MppServiceImpl<EventManagerMapper, EventManager> implements EventManagerService
{
}