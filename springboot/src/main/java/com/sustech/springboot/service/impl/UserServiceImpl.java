
package com.sustech.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.springboot.entity.User;
import com.sustech.springboot.mapper.UserMapper;
import com.sustech.springboot.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService 
{
}