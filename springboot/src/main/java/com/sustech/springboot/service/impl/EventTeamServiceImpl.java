
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventTeam;
import com.sustech.springboot.mapper.EventTeamMapper;
import com.sustech.springboot.service.EventTeamService;
import org.springframework.stereotype.Service;

@Service
public class EventTeamServiceImpl extends MppServiceImpl<EventTeamMapper, EventTeam> implements EventTeamService
{
}