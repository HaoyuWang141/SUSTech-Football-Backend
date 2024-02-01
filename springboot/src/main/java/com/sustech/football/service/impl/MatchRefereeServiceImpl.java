
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchReferee;
import com.sustech.football.mapper.MatchRefereeMapper;
import com.sustech.football.service.MatchRefereeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchRefereeServiceImpl extends MppServiceImpl<MatchRefereeMapper, MatchReferee> implements MatchRefereeService
{
    @Override
    public List<MatchReferee> listWithReferee(Long matchId) {
        return baseMapper.selectWithReferee(matchId);
    }

    @Override
    public List<MatchReferee> listWithMatch(Long refereeId) {
        return baseMapper.selectWithMatch(refereeId);
    }
}