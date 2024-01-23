
package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.Referee;
import com.sustech.football.mapper.RefereeMapper;
import com.sustech.football.service.RefereeService;
import org.springframework.stereotype.Service;

@Service
public class RefereeServiceImpl extends ServiceImpl<RefereeMapper, Referee> implements RefereeService 
{
}