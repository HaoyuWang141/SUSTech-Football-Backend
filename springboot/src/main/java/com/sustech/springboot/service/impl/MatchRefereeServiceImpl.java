
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.sustech.springboot.entity.MatchReferee;
import com.sustech.springboot.mapper.MatchRefereeMapper;
import com.sustech.springboot.service.MatchRefereeService;
import org.springframework.stereotype.Service;

@Service
public class MatchRefereeServiceImpl extends MppServiceImpl<MatchRefereeMapper, MatchReferee> implements MatchRefereeService
{
}