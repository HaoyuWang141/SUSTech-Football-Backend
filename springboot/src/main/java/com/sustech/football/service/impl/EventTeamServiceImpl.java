
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventTeam;
import com.sustech.football.mapper.EventTeamMapper;
import com.sustech.football.service.EventTeamService;
import org.springframework.stereotype.Service;

@Service
public class EventTeamServiceImpl extends MppServiceImpl<EventTeamMapper, EventTeam> implements EventTeamService
{
}