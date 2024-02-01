
package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.EventReferee;

import java.util.List;

public interface EventRefereeService extends IMppService<EventReferee>
{
    List<EventReferee> listWithEvent(Long refereeId);
    List<EventReferee> listWithReferee(Long eventId);
}