
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.TeamUniform;
import com.sustech.football.mapper.TeamUniformMapper;
import com.sustech.football.service.TeamUniformService;
import org.springframework.stereotype.Service;

@Service
public class TeamUniformServiceImpl extends MppServiceImpl<TeamUniformMapper, TeamUniform> implements TeamUniformService
{
}