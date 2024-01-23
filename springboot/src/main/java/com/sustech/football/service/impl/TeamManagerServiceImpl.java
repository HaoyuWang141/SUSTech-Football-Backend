
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamManager;
import com.sustech.football.mapper.TeamManagerMapper;
import com.sustech.football.service.TeamManagerService;
import org.springframework.stereotype.Service;

@Service
public class TeamManagerServiceImpl extends MppServiceImpl<TeamManagerMapper, TeamManager> implements TeamManagerService
{
}