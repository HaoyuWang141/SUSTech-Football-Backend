
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Coach;
import com.sustech.football.mapper.CoachMapper;
import com.sustech.football.service.CoachService;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService 
{
}