package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventRefereeRequest;
import com.sustech.football.mapper.EventRefereeRequestMapper;
import com.sustech.football.service.EventRefereeRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRefereeRequestServiceImpl extends MppServiceImpl<EventRefereeRequestMapper, EventRefereeRequest> implements EventRefereeRequestService {
    @Override
    public List<EventRefereeRequest> listWithEvent(Long refereeId) {
        return baseMapper.selectListWithEvent(refereeId);
    }

    @Override
    public List<EventRefereeRequest> listWithReferee(Long eventId) {
        return baseMapper.selectListWithReferee(eventId);
    }
}
