
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventManager;
import com.sustech.springboot.mapper.EventManagerMapper;
import com.sustech.springboot.service.EventManagerService;
import org.springframework.stereotype.Service;

@Service
public class EventManagerServiceImpl extends MppServiceImpl<EventManagerMapper, EventManager> implements EventManagerService
{
}