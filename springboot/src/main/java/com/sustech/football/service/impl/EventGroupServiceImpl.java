package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.sustech.football.entity.EventGroup;
import com.sustech.football.mapper.EventGroupMapper;
import com.sustech.football.service.EventGroupService;
import org.springframework.stereotype.Service;

@Service
public class EventGroupServiceImpl extends ServiceImpl<EventGroupMapper, EventGroup> implements EventGroupService {
}
