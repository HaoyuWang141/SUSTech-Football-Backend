
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamCoach;
import com.sustech.springboot.mapper.TeamCoachMapper;
import com.sustech.springboot.service.TeamCoachService;
import org.springframework.stereotype.Service;

@Service
public class TeamCoachServiceImpl extends MppServiceImpl<TeamCoachMapper, TeamCoach> implements TeamCoachService
{
}