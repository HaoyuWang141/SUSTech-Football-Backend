package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.MatchCreator;
import com.sustech.football.mapper.MatchCreatorMapper;
import com.sustech.football.service.MatchCreatorService;
import org.springframework.stereotype.Service;

@Service
public class MatchCreatorServiceImpl extends ServiceImpl<MatchCreatorMapper, MatchCreator> implements MatchCreatorService {
}
