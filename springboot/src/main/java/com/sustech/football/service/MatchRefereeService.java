
package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.MatchReferee;

import java.util.List;

public interface MatchRefereeService extends IMppService<MatchReferee> {
    List<MatchReferee> listWithReferee(Long matchId);
    List<MatchReferee> listWithMatch(Long refereeId);
}