package com.sustech.springboot.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.EventRefereeRequest;
import com.sustech.springboot.mapper.EventRefereeRequestMapper;
import com.sustech.springboot.service.EventRefereeRequestService;
import org.springframework.stereotype.Service;

@Service
public class EventRefereeRequestServiceImpl extends MppServiceImpl<EventRefereeRequestMapper, EventRefereeRequest> implements EventRefereeRequestService {
}
