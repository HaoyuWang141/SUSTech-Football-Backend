
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.MatchPlayerAction;
import com.sustech.springboot.mapper.MatchPlayerActionMapper;
import com.sustech.springboot.service.MatchPlayerActionService;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayerActionServiceImpl extends MppServiceImpl<MatchPlayerActionMapper, MatchPlayerAction> implements MatchPlayerActionService
{
}