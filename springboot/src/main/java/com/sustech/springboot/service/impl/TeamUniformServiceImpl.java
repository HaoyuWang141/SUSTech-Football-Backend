
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.TeamUniform;
import com.sustech.springboot.mapper.TeamUniformMapper;
import com.sustech.springboot.service.TeamUniformService;
import org.springframework.stereotype.Service;

@Service
public class TeamUniformServiceImpl extends MppServiceImpl<TeamUniformMapper, TeamUniform> implements TeamUniformService
{
}