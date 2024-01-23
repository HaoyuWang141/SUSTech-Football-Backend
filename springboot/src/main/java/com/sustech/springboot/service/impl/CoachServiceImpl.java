
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.springboot.entity.Coach;
import com.sustech.springboot.mapper.CoachMapper;
import com.sustech.springboot.service.CoachService;
import org.springframework.stereotype.Service;

@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService 
{
}