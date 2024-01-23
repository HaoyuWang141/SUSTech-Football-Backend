
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.springboot.entity.Referee;
import com.sustech.springboot.mapper.RefereeMapper;
import com.sustech.springboot.service.RefereeService;
import org.springframework.stereotype.Service;

@Service
public class RefereeServiceImpl extends ServiceImpl<RefereeMapper, Referee> implements RefereeService 
{
}