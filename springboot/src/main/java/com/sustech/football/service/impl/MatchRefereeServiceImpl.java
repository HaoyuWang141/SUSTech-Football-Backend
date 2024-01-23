
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchReferee;
import com.sustech.football.mapper.MatchRefereeMapper;
import com.sustech.football.service.MatchRefereeService;
import org.springframework.stereotype.Service;

@Service
public class MatchRefereeServiceImpl extends MppServiceImpl<MatchRefereeMapper, MatchReferee> implements MatchRefereeService
{
}