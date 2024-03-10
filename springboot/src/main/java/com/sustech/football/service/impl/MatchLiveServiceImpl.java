package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.MatchLive;
import com.sustech.football.mapper.MatchLiveMapper;
import com.sustech.football.service.MatchLiveService;
import org.springframework.stereotype.Service;

@Service
public class MatchLiveServiceImpl extends ServiceImpl<MatchLiveMapper, MatchLive> implements MatchLiveService {
}
