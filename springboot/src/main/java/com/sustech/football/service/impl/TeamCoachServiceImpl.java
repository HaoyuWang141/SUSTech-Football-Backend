
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamCoach;
import com.sustech.football.mapper.TeamCoachMapper;
import com.sustech.football.service.TeamCoachService;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachServiceImpl extends MppServiceImpl<TeamCoachMapper, TeamCoach> implements TeamCoachService
{
}