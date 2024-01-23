
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventReferee;
import com.sustech.springboot.mapper.EventRefereeMapper;
import com.sustech.springboot.service.EventRefereeService;
import org.springframework.stereotype.Service;

@Service
public class EventRefereeServiceImpl extends MppServiceImpl<EventRefereeMapper, EventReferee> implements EventRefereeService
{
}