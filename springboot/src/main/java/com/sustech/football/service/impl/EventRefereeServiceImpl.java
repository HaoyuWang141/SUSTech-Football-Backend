
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventReferee;
import com.sustech.football.mapper.EventRefereeMapper;
import com.sustech.football.service.EventRefereeService;
import org.springframework.stereotype.Service;

@Service
public class EventRefereeServiceImpl extends MppServiceImpl<EventRefereeMapper, EventReferee> implements EventRefereeService
{
}