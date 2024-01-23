
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamPlayer;
import com.sustech.springboot.mapper.TeamPlayerMapper;
import com.sustech.springboot.service.TeamPlayerService;
import org.springframework.stereotype.Service;

@Service
public class TeamPlayerServiceImpl extends MppServiceImpl<TeamPlayerMapper, TeamPlayer> implements TeamPlayerService
{
}