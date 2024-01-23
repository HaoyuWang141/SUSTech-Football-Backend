
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamManager;
import com.sustech.springboot.mapper.TeamManagerMapper;
import com.sustech.springboot.service.TeamManagerService;
import org.springframework.stereotype.Service;

@Service
public class TeamManagerServiceImpl extends MppServiceImpl<TeamManagerMapper, TeamManager> implements TeamManagerService
{
}