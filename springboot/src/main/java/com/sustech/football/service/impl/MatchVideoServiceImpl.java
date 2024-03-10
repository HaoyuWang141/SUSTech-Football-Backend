package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.MatchVideo;
import com.sustech.football.mapper.MatchVideoMapper;
import com.sustech.football.service.MatchVideoService;
import org.springframework.stereotype.Service;

@Service
public class MatchVideoServiceImpl extends ServiceImpl<MatchVideoMapper, MatchVideo> implements MatchVideoService {
}
