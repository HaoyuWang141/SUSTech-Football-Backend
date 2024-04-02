package com.sustech.football.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.sustech.football.entity.MatchRefereeRequest;

import java.util.List;

public interface MatchRefereeRequestService extends IMppService<MatchRefereeRequest> {
    List<MatchRefereeRequest> listWithMatch(Long refereeId);
    List<MatchRefereeRequest> listWithReferee(Long matchId);
}
