
package com.sustech.football.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.football.entity.MatchPlayerAction;
import com.sustech.football.mapper.MatchPlayerActionMapper;
import com.sustech.football.service.MatchPlayerActionService;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayerActionServiceImpl extends MppServiceImpl<MatchPlayerActionMapper, MatchPlayerAction> implements MatchPlayerActionService
{
}