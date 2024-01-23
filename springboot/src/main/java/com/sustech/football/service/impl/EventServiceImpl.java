
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Event;
import com.sustech.football.mapper.EventMapper;
import com.sustech.football.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService 
{
}