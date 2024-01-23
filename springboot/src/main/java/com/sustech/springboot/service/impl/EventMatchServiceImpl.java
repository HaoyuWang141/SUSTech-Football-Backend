
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventMatch;
import com.sustech.springboot.mapper.EventMatchMapper;
import com.sustech.springboot.service.EventMatchService;
import org.springframework.stereotype.Service;

@Service
public class EventMatchServiceImpl extends MppServiceImpl<EventMatchMapper, EventMatch> implements EventMatchService
{
}