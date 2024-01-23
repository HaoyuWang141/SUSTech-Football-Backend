
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.springboot.entity.Match;
import com.sustech.springboot.mapper.MatchMapper;
import com.sustech.springboot.service.MatchService;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements MatchService 
{
}