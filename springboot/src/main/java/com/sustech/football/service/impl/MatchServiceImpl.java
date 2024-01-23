
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Match;
import com.sustech.football.mapper.MatchMapper;
import com.sustech.football.service.MatchService;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements MatchService 
{
}