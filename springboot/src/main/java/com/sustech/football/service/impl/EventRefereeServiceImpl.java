
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.EventReferee;
import com.sustech.football.mapper.EventRefereeMapper;
import com.sustech.football.service.EventRefereeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRefereeServiceImpl extends MppServiceImpl<EventRefereeMapper, EventReferee> implements EventRefereeService {
    @Override
    public List<EventReferee> listWithEvent(Long refereeId) {
        return baseMapper.selectListWithEvent(refereeId);
    }

    @Override
    public List<EventReferee> listWithReferee(Long eventId) {
        return baseMapper.selectListWithReferee(eventId);
    }
}