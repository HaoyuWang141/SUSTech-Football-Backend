package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.TeamCreator;
import com.sustech.football.mapper.TeamCreatorMapper;
import com.sustech.football.service.TeamCreatorService;
import org.springframework.stereotype.Service;

@Service
public class TeamCreatorServiceImpl extends ServiceImpl<TeamCreatorMapper, TeamCreator> implements TeamCreatorService {
}
