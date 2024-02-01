package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchRefereeRequest;
import com.sustech.football.mapper.MatchRefereeRequestMapper;
import com.sustech.football.service.MatchRefereeRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchRefereeRequestServiceImpl extends MppServiceImpl<MatchRefereeRequestMapper, MatchRefereeRequest> implements MatchRefereeRequestService {
    @Override
    public List<MatchRefereeRequest> listWithMatch(Long refereeId) {
        return baseMapper.selectWithMatch(refereeId);
    }

    @Override
    public List<MatchRefereeRequest> listWithReferee(Long matchId) {
        return baseMapper.selectWithReferee(matchId);
    }
}
