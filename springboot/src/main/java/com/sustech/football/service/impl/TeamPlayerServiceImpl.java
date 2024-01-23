
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamPlayer;
import com.sustech.football.mapper.TeamPlayerMapper;
import com.sustech.football.service.TeamPlayerService;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerServiceImpl extends MppServiceImpl<TeamPlayerMapper, TeamPlayer> implements TeamPlayerService
{
}